package com.mycompany.sentry.service;

import com.mycompany.sentry.entity.User;
import com.mycompany.sentry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Sử dụng PasswordEncoder để so sánh mật khẩu đã mã hóa
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}