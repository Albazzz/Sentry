package com.mycompany.sentry.controller;

import com.mycompany.sentry.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("currentURI", request.getRequestURI());

        // Tự động thêm thông tin user vào model nếu đã đăng nhập
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUser());
            model.addAttribute("userRole", userDetails.getAuthorities());
        }

        return "index"; // trỏ đến /templates/index.html
    }
}
