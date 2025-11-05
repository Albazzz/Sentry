package com.mycompany.sentry.security;

import org.springframework.stereotype.Component;

/**
 * Input Sanitizer - Xử lý bảo vệ chống XSS
 */
@Component
public class InputSanitizer {

    /**
     * Sanitize input string để bảo vệ chống XSS attack
     */
    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }

        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;")
                .replace("/", "&#x2F;");
    }
}
