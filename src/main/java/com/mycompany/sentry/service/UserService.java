package com.mycompany.sentry.service;


import com.mycompany.sentry.entity.User;
import com.mycompany.sentry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // So sánh password trực tiếp (đơn giản, thực tế nên dùng BCryptPasswordEncoder)
            return password.equals(user.getPassword());
        }
        return false;
    }
}