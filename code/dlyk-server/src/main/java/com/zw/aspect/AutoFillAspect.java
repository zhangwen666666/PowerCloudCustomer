package com.zw.aspect;

import com.zw.annotations.AutoFill;
import com.zw.entity.TUser;
import com.zw.enums.OperationType;
import com.zw.util.UserInfoUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class AutoFillAspect {
    @Pointcut("execution(* com.zw.mapper.*.*(..)) && @annotation(com.zw.annotations.AutoFill)")
    public void autoFillPointcut(){}

    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0){
            return;
        }
        TUser user = (TUser) args[0];
        if (OperationType.INSERT.equals(operationType)){
            user.setCreateTime(new Date());
            user.setCreateBy(UserInfoUtil.getCurrentUser().getId());
        }
        user.setEditTime(new Date());
        user.setEditBy(UserInfoUtil.getCurrentUser().getId());
    }
}
