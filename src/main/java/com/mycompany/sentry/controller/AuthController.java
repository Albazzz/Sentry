package com.mycompany.sentry.controller;

import com.mycompany.sentry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "Authentication/login"; // Trả về template login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        @RequestParam(required = false) String rememberMe,
                        HttpSession session, Model model) {
        if (userService.authenticate(email, password)) {
            // Lưu session đơn giản
            session.setAttribute("authUser", userService.findByEmail(email).get());
            return "redirect:/"; // Chuyển về trang chủ (index)
        } else {
            model.addAttribute("message", "Email hoặc mật khẩu không đúng!");
            return "Authentication/login"; // Quay lại form với lỗi
        }
    }

    // Các mapping khác: /signup, /forgot-password có thể thêm sau
}