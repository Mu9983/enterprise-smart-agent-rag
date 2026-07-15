package com.mu9983.filter;

import com.mu9983.utils.JwtUtils;
import com.mu9983.utils.UserContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("token");
        UserContext.setToken(token);
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (token == null || token.isEmpty()) {
            log.error("令牌为空，拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.error("令牌有误，拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
