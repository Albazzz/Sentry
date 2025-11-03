package com.mycompany.sentry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry; // Thêm import
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver; // Đổi import
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor; // Thêm import

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    /**
     * Sửa: Đổi sang CookieLocaleResolver
     * Nó sẽ lưu ngôn ngữ người dùng chọn vào một cookie.
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("vi")); // Mặc định tiếng Việt
        resolver.setCookieName("user-lang"); // Tên của cookie
        resolver.setCookieMaxAge(3600 * 24 * 30); // 30 ngày
        return resolver;
    }

    /**
     * Mới: Thêm Interceptor để lắng nghe tham số URL
     * Ví dụ: ?lang=en hoặc ?lang=vi
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Tên tham số trên URL
        return lci;
    }

    /**
     * Mới: Đăng ký Interceptor với Spring
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}