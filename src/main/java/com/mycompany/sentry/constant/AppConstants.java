package com.mycompany.sentry.constant;

/**
 * Hằng số toàn cục cho ứng dụng
 */
public class AppConstants {

    // ===== OTP CONSTANTS =====
    public static final int OTP_LENGTH = 6;
    public static final int OTP_EXPIRY_MINUTES = 5;

    // ===== VALIDATION CONSTANTS =====
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 50;
    public static final int MIN_FULLNAME_LENGTH = 2;
    public static final int MAX_FULLNAME_LENGTH = 100;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final String PHONE_REGEX = "^(\\+84|0)[0-9]{8,9}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$";
    public static final String FULLNAME_REGEX = "^[^0-9]*$";
    public static final String GENDER_REGEX = "^(Nam|Nữ|Khác)$";

    // Password strength thresholds
    public static final int PASSWORD_STRENGTH_WEAK_THRESHOLD = 2;
    public static final int PASSWORD_STRENGTH_MEDIUM_THRESHOLD = 4;

    // Password strength levels
    public static final String PASSWORD_STRENGTH_NONE = "none";
    public static final String PASSWORD_STRENGTH_WEAK = "weak";
    public static final String PASSWORD_STRENGTH_MEDIUM = "medium";
    public static final String PASSWORD_STRENGTH_STRONG = "strong";

    // ===== FILE UPLOAD CONSTANTS =====
    public static final long MAX_CERTIFICATE_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String ALLOWED_CERTIFICATE_EXTENSION = ".pdf";

    // ===== ROLE CONSTANTS =====
    public static final Integer ROLE_FREE_USER = 1;
    public static final Integer ROLE_PREMIUM_USER = 2;
    public static final Integer ROLE_TEACHER = 3;
    public static final Integer ROLE_ADMIN = 4;
    public static final long MIN_ROLE_ID = 1L;
    public static final long MAX_ROLE_ID = 4L;

    // ===== UI CONSTANTS =====
    public static final String DEFAULT_GENDER = "Khác";

    // ===== ERROR MESSAGES =====
    public static final String MSG_EMAIL_ALREADY_EXISTS = "Email đã được sử dụng";
    public static final String MSG_REGISTRATION_SUCCESS = "Đăng ký thành công. Vui lòng kiểm tra email để lấy mã OTP.";
    public static final String MSG_REGISTRATION_ERROR = "Đã xảy ra lỗi khi đăng ký";
    public static final String MSG_OTP_SEND_FAILED = "Không thể gửi email OTP cho ";

    // ===== VALIDATION MESSAGES =====
    public static final String MSG_VALIDATION_EMAIL_EMPTY = "Email không được để trống.";
    public static final String MSG_VALIDATION_EMAIL_EXISTS = "Email đã được sử dụng.";
    public static final String MSG_VALIDATION_EMAIL_AVAILABLE = "Email có thể sử dụng.";
    public static final String MSG_VALIDATION_EMAIL_CHECK_ERROR = "Lỗi khi kiểm tra email.";
    public static final String MSG_VALIDATION_PASSWORD_EMPTY = "Mật khẩu không được để trống.";
    public static final String MSG_VALIDATION_PASSWORD_WEAK = "Mật khẩu yếu. Nên thêm chữ hoa, chữ thường, số và ký tự đặc biệt.";
    public static final String MSG_VALIDATION_PASSWORD_MEDIUM = "Mật khẩu trung bình. Có thể cải thiện bằng cách thêm ký tự đặc biệt.";
    public static final String MSG_VALIDATION_PASSWORD_STRONG = "Mật khẩu mạnh.";

    // ===== OTP VERIFICATION MESSAGES =====
    public static final String MSG_OTP_VERIFY_SUCCESS = "Xác thực OTP thành công. Tài khoản đã được kích hoạt.";
    public static final String MSG_OTP_VERIFY_INVALID = "Mã OTP không đúng hoặc đã hết hạn.";
    public static final String MSG_OTP_VERIFY_USER_NOT_FOUND = "Không tìm thấy người dùng với email này.";
    public static final String MSG_OTP_VERIFY_ERROR = "Lỗi khi xác thực OTP: ";

    // ===== OTP RESEND MESSAGES =====
    public static final String MSG_OTP_RESEND_EMAIL_NOT_FOUND = "Email không tồn tại hoặc chưa đăng ký.";
    public static final String MSG_OTP_RESEND_ALREADY_ACTIVE = "Tài khoản đã được kích hoạt.";
    public static final String MSG_OTP_RESEND_SUCCESS = "Mã OTP mới đã được gửi đến email của bạn.";
    public static final String MSG_OTP_RESEND_EMAIL_FAILED = "Không thể gửi lại mã OTP. Vui lòng thử lại sau.";

    // ===== AUTHENTICATION MESSAGES =====
    public static final String MSG_AUTH_LOGIN_ERROR = "Đăng nhập thất bại. Vui lòng kiểm tra lại email và mật khẩu.";
    public static final String MSG_AUTH_LOGOUT_SUCCESS = "Đăng xuất thành công.";
    public static final String MSG_AUTH_SESSION_EXPIRED = "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.";
    public static final String MSG_AUTH_ACCESS_DENIED = "Bạn không có quyền truy cập trang này.";

    // ===== EMAIL CONSTANTS =====
    public static final String EMAIL_FROM_ADDRESS = "noreply@sentry.com";
    public static final String EMAIL_OTP_SUBJECT = "Mã OTP xác thực đăng ký - Sentry";
    public static final String EMAIL_OTP_GREETING = "Xin chào!";
    public static final String EMAIL_OTP_BODY_REGISTRATION = "Bạn đã đăng ký tài khoản trên Sentry.";
    public static final String EMAIL_OTP_BODY_CODE = "Mã OTP của bạn là: ";
    public static final String EMAIL_OTP_BODY_EXPIRY = "Mã OTP này sẽ hết hạn sau ";
    public static final String EMAIL_OTP_BODY_EXPIRY_UNIT = " phút.";
    public static final String EMAIL_OTP_BODY_WARNING = "Vui lòng không chia sẻ mã này với bất kỳ ai.";
    public static final String EMAIL_OTP_SIGNATURE = "Trân trọng,\nĐội ngũ Sentry";

    // ===== RATE LIMITING CONSTANTS =====
    public static final int RATE_LIMIT_MAX_REQUESTS = 100;
    public static final long RATE_LIMIT_TIME_WINDOW_MS = 60000; // 1 phút
    public static final int HTTP_STATUS_TOO_MANY_REQUESTS = 429;
    public static final String RATE_LIMIT_EXCEEDED_RESPONSE = "{\"error\":\"Too many requests. Please try again later.\"}";
}
