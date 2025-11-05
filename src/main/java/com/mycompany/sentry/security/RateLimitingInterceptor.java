package com.mycompany.sentry.security;

import com.mycompany.sentry.constant.AppConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rate Limiting Interceptor - Giới hạn số request từ một IP
 */
@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final ConcurrentHashMap<String, RequestCounter> requestCounters = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = getClientIp(request);

        RequestCounter counter = requestCounters.computeIfAbsent(clientIp, k -> new RequestCounter());

        if (counter.isExpired()) {
            counter.reset();
        }

        if (counter.incrementAndGet() > AppConstants.RATE_LIMIT_MAX_REQUESTS) {
            response.setStatus(AppConstants.HTTP_STATUS_TOO_MANY_REQUESTS);
            response.getWriter().write(AppConstants.RATE_LIMIT_EXCEEDED_RESPONSE);
            return false;
        }

        return true;
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    private static class RequestCounter {
        private final AtomicInteger count = new AtomicInteger(0);
        private long startTime = System.currentTimeMillis();

        public int incrementAndGet() {
            return count.incrementAndGet();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - startTime > AppConstants.RATE_LIMIT_TIME_WINDOW_MS;
        }

        public void reset() {
            count.set(0);
            startTime = System.currentTimeMillis();
        }
    }
}
