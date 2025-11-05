package com.mycompany.sentry.controller;

import com.mycompany.sentry.entity.User;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AICallController {

    @GetMapping("/aicall")
    public String showAICall(HttpSession session) {
        User user = (User) session.getAttribute("authUser");

        if (user == null) {
            return "redirect:/login";
        }

        // Trả về đúng file: templates/AI/aicall.html
        return "AI/aicall";
    }
}