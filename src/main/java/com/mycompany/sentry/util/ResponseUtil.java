package com.mycompany.sentry.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class để tạo response chuẩn hóa cho API
 */
public class ResponseUtil {

    /**
     * Tạo success response
     */
    public static Map<String, Object> success(String message) {
        return success(message, null);
    }

    /**
     * Tạo success response với data
     */
    public static Map<String, Object> success(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    /**
     * Tạo error response
     */
    public static Map<String, Object> error(String message) {
        return error(message, null);
    }

    /**
     * Tạo error response với chi tiết
     */
    public static Map<String, Object> error(String message, Object details) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("data", details);
        return response;
    }

    /**
     * Tạo response validation với tên field
     */
    public static Map<String, Object> validationError(String fieldName, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("field", fieldName);
        error.put("message", message);
        return error(message, error);
    }

    /**
     * Tạo response check email
     */
    public static Map<String, Object> emailCheckResponse(boolean exists, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("valid", !exists);
        response.put("message", message);
        return response;
    }

    /**
     * Tạo response password strength check
     */
    public static Map<String, Object> passwordStrengthResponse(boolean valid, String strength, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("valid", valid);
        response.put("strength", strength);
        response.put("message", message);
        return response;
    }
}

