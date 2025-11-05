package com.mycompany.sentry.service;

import com.mycompany.sentry.constant.AppConstants;
import com.mycompany.sentry.dto.RegisterRequest;
import com.mycompany.sentry.entity.User;
import com.mycompany.sentry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private OTPService otpService;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Kiểm tra user đã active chưa
            if (user.getIsActive() == null || !user.getIsActive()) {
                return false; // User chưa verify OTP
            }
            // Sử dụng PasswordEncoder để so sánh mật khẩu đã mã hóa
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
    
    /**
     * Đăng ký người dùng mới
     * Mật khẩu sẽ được mã hóa bằng BCryptPasswordEncoder trước khi lưu vào database
     * 
     * @param request Thông tin đăng ký từ client
     * @return User đã được tạo
     * @throws RuntimeException nếu email đã tồn tại
     */
    public User registerUser(RegisterRequest request) {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException(AppConstants.MSG_EMAIL_ALREADY_EXISTS);
        }
        
        // Tạo User mới
        User user = new User();
        user.setEmail(request.getEmail());
        
        // MÃ HÓA MẬT KHẨU trước khi lưu - QUAN TRỌNG!
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setJapaneseLevel(request.getJapaneseLevel());
        user.setGender(request.getGender() != null ? request.getGender() : AppConstants.DEFAULT_GENDER);
        user.setRoleID(request.getRoleID() != null ? request.getRoleID() : AppConstants.ROLE_FREE_USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(false); // Chưa active, phải verify OTP trước
        user.setIsBan(false);
        
        // Lưu vào database
        return userRepository.save(user);
    }
    
    /**
     * Đăng ký người dùng mới và gửi OTP trong cùng một transaction
     * Nếu gửi OTP thất bại, transaction sẽ rollback và user sẽ không được tạo
     * 
     * @param request Thông tin đăng ký từ client
     * @return User đã được tạo
     * @throws RuntimeException nếu email đã tồn tại hoặc không thể gửi OTP
     */
    @Transactional(rollbackFor = Exception.class)
    public User registerUserWithOTP(RegisterRequest request) {
        // Đăng ký user
        User registeredUser = registerUser(request);
        
        // Tạo và gửi OTP - nếu thất bại sẽ throw exception và rollback
        String otpCode = otpService.generateAndSaveOTP(request.getEmail());
        otpService.sendOTPByEmailOrThrow(request.getEmail(), otpCode);
        
        return registeredUser;
    }
    
    /**
     * Kích hoạt user sau khi verify OTP thành công
     * @param email Email của user cần kích hoạt
     * @return true nếu thành công, false nếu không tìm thấy user
     */
    public boolean activateUser(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setIsActive(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
    /**
     * Kiểm tra user có active không
     */
    public boolean isUserActive(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            Boolean isActive = userOpt.get().getIsActive();
            return isActive != null && isActive;
        }
        return false;
    }
}