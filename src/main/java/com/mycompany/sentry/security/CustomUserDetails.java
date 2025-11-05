package com.mycompany.sentry.security;

import com.mycompany.sentry.constant.AppConstants;
import com.mycompany.sentry.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserDetails implement UserDetails của Spring Security
 * Wrapper cho User entity để tích hợp với Spring Security
 */
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map roleID sang role name
        String roleName;
        if (user.getRoleID().equals(AppConstants.ROLE_FREE_USER)) {
            roleName = "ROLE_USER"; // FreeUser
        } else if (user.getRoleID().equals(AppConstants.ROLE_PREMIUM_USER)) {
            roleName = "ROLE_PREMIUM"; // PremiumUser
        } else if (user.getRoleID().equals(AppConstants.ROLE_TEACHER)) {
            roleName = "ROLE_TEACHER"; // Teacher
        } else if (user.getRoleID().equals(AppConstants.ROLE_ADMIN)) {
            roleName = "ROLE_ADMIN"; // Admin
        } else {
            roleName = "ROLE_USER";
        }
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Sử dụng email làm username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getIsBan(); // Nếu bị ban thì lock account
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive() != null && user.getIsActive(); // Chỉ active nếu đã verify OTP
    }

    /**
     * Lấy User entity gốc
     */
    public User getUser() {
        return user;
    }
}
