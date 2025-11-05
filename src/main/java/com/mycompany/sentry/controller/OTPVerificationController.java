package com.mycompany.sentry.controller;

import com.mycompany.sentry.constant.AppConstants;
import com.mycompany.sentry.service.OTPService;
import com.mycompany.sentry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller xử lý xác thực OTP
 */
@RestController
@RequestMapping("/otp")
public class OTPVerificationController {
    
    @Autowired
    private OTPService otpService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Xác thực OTP
     * POST /otp/verify
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyOTP(
            @RequestParam String email,
            @RequestParam String otp) {
        
        Map<String, Object> response = new HashMap<>();

        try {
            // Xác thực OTP
            boolean isValid = otpService.verifyOTP(email, otp);
            
            if (isValid) {
                // Kích hoạt user
                boolean activated = userService.activateUser(email);
                
                if (activated) {
                    response.put("success", true);
                    response.put("message", AppConstants.MSG_OTP_VERIFY_SUCCESS);
                    return ResponseEntity.ok(response);
                } else {
                    response.put("success", false);
                    response.put("message", AppConstants.MSG_OTP_VERIFY_USER_NOT_FOUND);
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("success", false);
                response.put("message", AppConstants.MSG_OTP_VERIFY_INVALID);
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", AppConstants.MSG_OTP_VERIFY_ERROR + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * Gửi lại mã OTP
     * POST /otp/resend
     */
    @PostMapping("/resend")
    public ResponseEntity<Map<String, Object>> resendOTP(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra email có tồn tại user chưa active không
            if (!userService.findByEmail(email).isPresent()) {
                response.put("success", false);
                response.put("message", AppConstants.MSG_OTP_RESEND_EMAIL_NOT_FOUND);
                return ResponseEntity.badRequest().body(response);
            }
            
            if (userService.isUserActive(email)) {
                response.put("success", false);
                response.put("message", AppConstants.MSG_OTP_RESEND_ALREADY_ACTIVE);
                return ResponseEntity.badRequest().body(response);
            }
            
            // Tạo và gửi OTP mới
            String otpCode = otpService.generateAndSaveOTP(email);
            boolean emailSent = otpService.sendOTPByEmail(email, otpCode);
            
            if (emailSent) {
                response.put("success", true);
                response.put("message", AppConstants.MSG_OTP_RESEND_SUCCESS);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", AppConstants.MSG_OTP_RESEND_EMAIL_FAILED);
                return ResponseEntity.status(500).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", AppConstants.MSG_OTP_VERIFY_ERROR + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
