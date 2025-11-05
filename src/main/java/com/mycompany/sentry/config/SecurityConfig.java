package com.mycompany.sentry.config;

import com.mycompany.sentry.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Bean PasswordEncoder sử dụng BCrypt để mã hóa mật khẩu
     * BCrypt là thuật toán mã hóa mạnh mẽ và an toàn
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * DaoAuthenticationProvider kết nối UserDetailsService với PasswordEncoder
     * Đây là cách Spring Security xác thực user từ database
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * AuthenticationManager quản lý quá trình xác thực
     * Cần thiết cho việc đăng nhập programmatically
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - không cần đăng nhập
                        .requestMatchers(
                                "/",                            // Trang chủ PUBLIC
                                "/index",                       // Trang chủ PUBLIC
                                "/login",
                                "/signup",
                                "/register/**",
                                "/otp/**",
                                "/api/validation/**",           // API validation cho form đăng ký
                                "/css/**",
                                "/js/**",
                                "/image/**",
                                "/error"
                        ).permitAll()

                        // Admin endpoints - chỉ admin mới truy cập được
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Teacher endpoints - teacher và admin
                        .requestMatchers("/teacher/**").hasAnyRole("TEACHER", "ADMIN")

                        // Premium endpoints - user premium, teacher và admin
                        .requestMatchers("/premium/**").hasAnyRole("PREMIUM", "TEACHER", "ADMIN")

                        // Các endpoint cần bảo vệ - yêu cầu đăng nhập
                        .requestMatchers("/profile", "/chat/**", "/videocall/**").authenticated()

                        // Các endpoint còn lại - cho phép truy cập
                        .anyRequest().permitAll()
                )

                // Cấu hình Form Login
                .formLogin(login -> login
                        .loginPage("/login")                    // Trang đăng nhập custom
                        .loginProcessingUrl("/login")           // URL xử lý đăng nhập
                        .usernameParameter("email")             // Tên parameter cho username (ở đây là email)
                        .passwordParameter("password")          // Tên parameter cho password
                        .defaultSuccessUrl("/", true)          // Redirect sau khi đăng nhập thành công
                        .failureUrl("/login?error=true")       // Redirect khi đăng nhập thất bại
                        .permitAll()
                )

                // Cấu hình Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")                   // URL để logout
                        .logoutSuccessUrl("/login?logout=true") // Redirect sau khi logout
                        .invalidateHttpSession(true)            // Xóa session
                        .deleteCookies("JSESSIONID")            // Xóa cookie session
                        .permitAll()
                )

                // Cấu hình Remember Me
                .rememberMe(remember -> remember
                        .key("sentrySecretKey")                 // Secret key cho remember me
                        .tokenValiditySeconds(7 * 24 * 60 * 60) // 7 ngày
                        .rememberMeParameter("rememberMe")      // Tên parameter
                )

                // Cấu hình Session Management
                .sessionManagement(session -> session
                        .maximumSessions(1)                     // Chỉ cho phép 1 session đồng thời
                        .maxSessionsPreventsLogin(false)        // Session mới sẽ kick session cũ
                        .expiredUrl("/login?expired=true")      // Redirect khi session hết hạn
                )

                // Cấu hình CSRF - bật đầy đủ, nhưng bỏ qua cho các endpoint công khai cần POST không có token
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/register/**",
                                "/otp/**",
                                "/api/validation/**"
                        )
                )

                // Cấu hình Exception Handling
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")     // Trang khi không có quyền truy cập
                );

        return http.build();
    }
}
