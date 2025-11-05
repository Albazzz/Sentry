package com.mycompany.sentry.service;

import com.mycompany.sentry.constant.AppConstants;
import com.mycompany.sentry.entity.OTP;
import com.mycompany.sentry.repository.OTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPService {
    
    private static final Logger logger = LoggerFactory.getLogger(OTPService.class);
    
    @Autowired
    private OTPRepository otpRepository;
    
    @Autowired(required = false)
    private JavaMailSender mailSender;

    /**
     * Tạo mã OTP 6 chữ số ngẫu nhiên
     */
    private String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < AppConstants.OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
    
    /**
     * Tạo và lưu OTP cho email
     * @param email Email cần gửi OTP
     * @return Mã OTP đã tạo
     */
    @Transactional
    public String generateAndSaveOTP(String email) {
        // Đánh dấu tất cả OTP cũ của email này là đã dùng
        otpRepository.markAllAsUsedByEmail(email);
        
        // Tạo OTP mới
        String otpCode = generateOTP();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(AppConstants.OTP_EXPIRY_MINUTES);

        OTP otp = new OTP();
        otp.setEmail(email);
        otp.setCode(otpCode);
        otp.setExpiresAt(expiresAt);
        otp.setIsUsed(false);
        otp.setCreatedAt(LocalDateTime.now());
        
        otpRepository.save(otp);

        return otpCode;
    }
    
    /**
     * Gửi OTP qua email (throw exception nếu thất bại - dùng trong transaction)
     * @param email Email nhận OTP
     * @param otpCode Mã OTP
     * @throws RuntimeException nếu không thể gửi email OTP
     */
    public void sendOTPByEmailOrThrow(String email, String otpCode) {
        if (!sendOTPByEmail(email, otpCode)) {
            throw new RuntimeException(AppConstants.MSG_OTP_SEND_FAILED + email);
        }
    }
    
    /**
     * Gửi OTP qua email
     * @param email Email nhận OTP
     * @param otpCode Mã OTP
     * @return true nếu gửi thành công, false nếu không
     */
    public boolean sendOTPByEmail(String email, String otpCode) {
        // Nếu có mailSender (đã cấu hình email), gửi email thật
        if (mailSender != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject(AppConstants.EMAIL_OTP_SUBJECT);
                message.setText(
                    AppConstants.EMAIL_OTP_GREETING + "\n\n" +
                    AppConstants.EMAIL_OTP_BODY_REGISTRATION + "\n" +
                    AppConstants.EMAIL_OTP_BODY_CODE + otpCode + "\n\n" +
                    AppConstants.EMAIL_OTP_BODY_EXPIRY + AppConstants.OTP_EXPIRY_MINUTES + AppConstants.EMAIL_OTP_BODY_EXPIRY_UNIT + "\n" +
                    AppConstants.EMAIL_OTP_BODY_WARNING + "\n\n" +
                    AppConstants.EMAIL_OTP_SIGNATURE
                );
                message.setFrom(AppConstants.EMAIL_FROM_ADDRESS);

                mailSender.send(message);
                logger.info("Email OTP đã được gửi đến: {}", email);
                return true;
            } catch (Exception e) {
                logger.error("Lỗi gửi email OTP đến: {}", email, e);
                // Fallback: in ra console nếu gửi email thất bại
                printOTPToConsole(email, otpCode);
                return false;
            }
        } else {
            // Nếu chưa cấu hình email, chỉ in ra console
            printOTPToConsole(email, otpCode);
            return true;
        }
    }
    
    /**
     * In OTP ra console (cho development hoặc khi không có email config)
     */
    private void printOTPToConsole(String email, String otpCode) {
        logger.info("OTP cho email: {}", email);
        logger.info("Mã OTP: {}", otpCode);
        logger.info("Mã OTP hết hạn sau: {} phút", AppConstants.OTP_EXPIRY_MINUTES);
    }
    
    /**
     * Xác thực OTP
     * @param email Email của user
     * @param code Mã OTP nhập vào
     * @return true nếu OTP hợp lệ, false nếu không
     */
    @Transactional
    public boolean verifyOTP(String email, String code) {
        LocalDateTime now = LocalDateTime.now();
        Optional<OTP> otpOpt = otpRepository.findByEmailAndCodeAndIsUsedFalseAndExpiresAtAfter(
            email, code, now
        );
        
        if (otpOpt.isPresent()) {
            OTP otp = otpOpt.get();
            otp.setIsUsed(true);
            otpRepository.save(otp);
            return true;
        }
        
        return false;
    }
    
    /**
     * Kiểm tra xem email có OTP hợp lệ chưa hết hạn không
     */
    public boolean hasValidOTP(String email) {
        LocalDateTime now = LocalDateTime.now();
        Optional<OTP> otpOpt = otpRepository.findTopByEmailAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(
            email, now
        );
        return otpOpt.isPresent();
    }
}
