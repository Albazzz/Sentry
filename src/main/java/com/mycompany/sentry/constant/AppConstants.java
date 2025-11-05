package com.mycompany.sentry.constant;

/**
 * Hằng số cơ bản cho ứng dụng
 */
public class AppConstants {

    // ===== ROLE CONSTANTS =====
    public static final Integer ROLE_FREE_USER = 1;
    public static final Integer ROLE_PREMIUM_USER = 2;
    public static final Integer ROLE_TEACHER = 3;
    public static final Integer ROLE_ADMIN = 4;

    // ===== DEFAULT VALUES =====
    public static final String DEFAULT_GENDER = "Khác";

    // ===== AUTHENTICATION MESSAGES =====
    public static final String MSG_AUTH_LOGIN_ERROR = "Đăng nhập thất bại. Vui lòng kiểm tra lại email và mật khẩu.";
    public static final String MSG_AUTH_LOGOUT_SUCCESS = "Đăng xuất thành công.";
    public static final String MSG_AUTH_SESSION_EXPIRED = "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.";
    public static final String MSG_AUTH_ACCESS_DENIED = "Bạn không có quyền truy cập trang này.";
}
