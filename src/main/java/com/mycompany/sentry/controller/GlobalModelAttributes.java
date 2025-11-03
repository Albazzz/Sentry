package com.mycompany.sentry.controller; // Hoặc package chung của bạn

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    /**
     * Tự động thêm thuộc tính "authUser" vào Model cho mọi request.
     * Giá trị được lấy từ session.
     */
    @ModelAttribute("authUser")
    public Object addAuthUserToModel(HttpSession session) {
        // Lấy "authUser" từ session và trả về
        // Nếu không có, nó sẽ trả về null, và th:if="${authUser == null}" sẽ hoạt động đúng
        return session.getAttribute("authUser");
    }
}