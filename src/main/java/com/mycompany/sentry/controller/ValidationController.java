package com.mycompany.sentry.controller;

import com.mycompany.sentry.constant.AppConstants;
import com.mycompany.sentry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller xử lý validation realtime cho form đăng ký
 */
@RestController
@RequestMapping("/api/validation")
public class ValidationController {

    @Autowired
    private UserService userService;

    /**
     * Kiểm tra email đã tồn tại chưa
     * GET /api/validation/check-email?email=test@gmail.com
     *
     * @param email Email cần kiểm tra
     * @return JSON {exists: true/false, message: "..."}
     */
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate email format cơ bản
            if (email == null || email.trim().isEmpty()) {
                response.put("exists", false);
                response.put("valid", false);
                response.put("message", AppConstants.MSG_VALIDATION_EMAIL_EMPTY);
                return ResponseEntity.badRequest().body(response);
            }

            // Kiểm tra email có tồn tại trong database không
            boolean exists = userService.findByEmail(email.trim()).isPresent();

            response.put("exists", exists);
            response.put("valid", !exists);

            if (exists) {
                response.put("message", AppConstants.MSG_VALIDATION_EMAIL_EXISTS);
            } else {
                response.put("message", AppConstants.MSG_VALIDATION_EMAIL_AVAILABLE);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("exists", false);
            response.put("valid", false);
            response.put("message", AppConstants.MSG_VALIDATION_EMAIL_CHECK_ERROR);
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Validate password strength
     * POST /api/validation/check-password
     *
     * @param request Request body containing password
     * @return JSON {valid: true/false, strength: "weak/medium/strong", message: "..."}
     */
    @PostMapping("/check-password")
    public ResponseEntity<Map<String, Object>> checkPassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String password = request.get("password");

        if (password == null || password.isEmpty()) {
            response.put("valid", false);
            response.put("strength", AppConstants.PASSWORD_STRENGTH_NONE);
            response.put("message", AppConstants.MSG_VALIDATION_PASSWORD_EMPTY);
            return ResponseEntity.ok(response);
        }

        // Kiểm tra độ mạnh mật khẩu
        int strength = 0;

        if (password.length() >= 6) strength++;
        if (password.length() >= 8) strength++;
        if (password.matches(".*[A-Z].*")) strength++; // Có chữ hoa
        if (password.matches(".*[a-z].*")) strength++; // Có chữ thường
        if (password.matches(".*\\d.*")) strength++;   // Có số
        if (password.matches(".*[@$!%*#?&].*")) strength++; // Có ký tự đặc biệt

        String strengthLevel;
        boolean valid;
        String message;

        if (strength < AppConstants.PASSWORD_STRENGTH_WEAK_THRESHOLD) {
            strengthLevel = AppConstants.PASSWORD_STRENGTH_WEAK;
            valid = false;
            message = AppConstants.MSG_VALIDATION_PASSWORD_WEAK;
        } else if (strength < AppConstants.PASSWORD_STRENGTH_MEDIUM_THRESHOLD) {
            strengthLevel = AppConstants.PASSWORD_STRENGTH_MEDIUM;
            valid = true;
            message = AppConstants.MSG_VALIDATION_PASSWORD_MEDIUM;
        } else {
            strengthLevel = AppConstants.PASSWORD_STRENGTH_STRONG;
            valid = true;
            message = AppConstants.MSG_VALIDATION_PASSWORD_STRONG;
        }

        response.put("valid", valid);
        response.put("strength", strengthLevel);
        response.put("message", message);

        return ResponseEntity.ok(response);
    }
}
