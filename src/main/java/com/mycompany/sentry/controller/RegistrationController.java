package com.mycompany.sentry.controller;

import com.mycompany.sentry.constant.AppConstants;
import com.mycompany.sentry.dto.RegisterRequest;
import com.mycompany.sentry.dto.RegisterResponse;
import com.mycompany.sentry.entity.User;
import com.mycompany.sentry.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller xử lý đăng ký người dùng mới
 * 
 * Endpoint: POST /register/user
 * - Nhận thông tin đăng ký từ client (email, password, fullName, ...)
 * - Mã hóa mật khẩu bằng BCryptPasswordEncoder
 * - Lưu thông tin người dùng vào database
 * - Trả về kết quả đăng ký
 */
@RestController
@RequestMapping("/register")
public class RegistrationController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Endpoint đăng ký người dùng mới
     * 
     * @param request Thông tin đăng ký (email, password, fullName, phoneNumber, japaneseLevel, gender, roleID)
     * @param bindingResult Kết quả validation
     * @return RegisterResponse chứa thông tin kết quả đăng ký
     */
    @PostMapping("/user")
    public ResponseEntity<RegisterResponse> registerUser(
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult) {
        
        // Kiểm tra validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            RegisterResponse response = new RegisterResponse(
                false,
                errorMessage,
                null,
                null
            );
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            // Đăng ký user và gửi OTP trong cùng transaction
            // Nếu gửi OTP thất bại, transaction sẽ rollback và user sẽ không được tạo
            User registeredUser = userService.registerUserWithOTP(request);

            // Tạo response thành công - yêu cầu verify OTP
            RegisterResponse response = new RegisterResponse(
                true,
                AppConstants.MSG_REGISTRATION_SUCCESS,
                registeredUser.getUserID(),
                registeredUser.getEmail()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (RuntimeException e) {
            // Xử lý lỗi (ví dụ: email đã tồn tại)
            RegisterResponse response = new RegisterResponse(
                false,
                e.getMessage(),
                null,
                null
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

        } catch (Exception e) {
            // Xử lý lỗi không mong muốn
            RegisterResponse response = new RegisterResponse(
                false,
                AppConstants.MSG_REGISTRATION_ERROR + ": " + e.getMessage(),
                null,
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
