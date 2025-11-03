package com.mycompany.sentry.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "index"; // trỏ đến /templates/index.html
    }
}
