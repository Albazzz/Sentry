package com.mycompany.sentry.dto;

import com.mycompany.sentry.constant.AppConstants;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = AppConstants.MAX_EMAIL_LENGTH, message = "Email không được quá 100 ký tự")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = AppConstants.MIN_PASSWORD_LENGTH, max = AppConstants.MAX_PASSWORD_LENGTH,
           message = "Mật khẩu phải có từ 6-50 ký tự")
    @Pattern(regexp = AppConstants.PASSWORD_REGEX,
             message = "Mật khẩu phải chứa ít nhất 1 chữ cái, 1 chữ số và 1 ký tự đặc biệt")
    private String password;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = AppConstants.MIN_FULLNAME_LENGTH, max = AppConstants.MAX_FULLNAME_LENGTH,
           message = "Họ tên phải có từ 2-100 ký tự")
    @Pattern(regexp = AppConstants.FULLNAME_REGEX, message = "Họ tên không được chứa số")
    private String fullName;

    @Pattern(regexp = AppConstants.PHONE_REGEX, message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
    
    private String japaneseLevel;
    
    @Pattern(regexp = AppConstants.GENDER_REGEX, message = "Giới tính phải là Nam, Nữ hoặc Khác")
    private String gender;
    
    @NotNull(message = "RoleID không được để trống")
    @Min(value = AppConstants.MIN_ROLE_ID, message = "RoleID không hợp lệ")
    @Max(value = AppConstants.MAX_ROLE_ID, message = "RoleID không hợp lệ")
    private Integer roleID = AppConstants.ROLE_FREE_USER;
}
