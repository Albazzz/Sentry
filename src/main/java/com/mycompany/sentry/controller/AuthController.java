package com.mycompany.sentry.controller;

import com.mycompany.sentry.constant.AppConstants;
import com.mycompany.sentry.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    /**
     * Hiển thị form đăng nhập
     * Spring Security sẽ tự động xử lý POST /login
     */
    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "expired", required = false) String expired,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", AppConstants.MSG_AUTH_LOGIN_ERROR);
        }
        if (logout != null) {
            model.addAttribute("successMessage", AppConstants.MSG_AUTH_LOGOUT_SUCCESS);
        }
        if (expired != null) {
            model.addAttribute("warningMessage", AppConstants.MSG_AUTH_SESSION_EXPIRED);
        }

        return "Authentication/login";
    }
    
    /**
     * Hiển thị form đăng ký
     */
    @GetMapping("/signup")
    public String showRegisterForm(Model model) {
        return "Authentication/register";
    }

    /**
     * Trang access denied
     */
    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("errorMessage", AppConstants.MSG_AUTH_ACCESS_DENIED);
        return "error/access-denied";
    }

    /**
     * Endpoint test để xem user hiện tại đã đăng nhập
     * @AuthenticationPrincipal tự động inject user đang đăng nhập
     */
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        return "profile";
    }
}