package com.mu9983.aspect;

import com.mu9983.entity.User;
import com.mu9983.exception.PermissionException;
import com.mu9983.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AOP切面类
 */
@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private UserService userService;

    @Pointcut("@annotation(com.mu9983.annotation.RequireSuperAdmin)")
    public void annotationPointCut() {}

    @Around("annotationPointCut()")
    public Object checkAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = userService.currentUser();
        if (user == null) {
            throw new Exception("未登录");
        }
        if (user.getPermission() == 0) {
            throw new PermissionException("权限异常");
        }
        return joinPoint.proceed();
    }

}
