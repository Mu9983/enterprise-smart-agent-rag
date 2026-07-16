package com.mu9983.filter;

import com.mu9983.utils.JwtUtils;
import com.mu9983.utils.UserContext;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 过滤器，拦截所有请求，只放行登录
 */
@Component
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String tokenStr = request.getHeader("token");
        Map<String, String> token = new HashMap<>();
        if (tokenStr != null) {
            String[] tokenStrArray = tokenStr.split("\"");
            // 将token交给ThreadLocal，以便在controller中复用
            token.put(tokenStrArray[1], tokenStrArray[3]);
            token.put(tokenStrArray[5], tokenStrArray[7]);
            UserContext.setToken(token);
        }
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (token.isEmpty() || token.get("access_token") == null) {
            log.error("令牌为空，拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (requestURI.contains("/refresh")) {
            log.info("刷新令牌请求，放行");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        try {
            JwtUtils.parseToken(token.get("access_token"));
        } catch (ExpiredJwtException e){
            log.error("令牌过期，拦截");
            // AccessToken过期，通知前端调用刷新接口
            response.setStatus(460);
            return;
        } catch (Exception e) {
            log.error("令牌有误，拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
