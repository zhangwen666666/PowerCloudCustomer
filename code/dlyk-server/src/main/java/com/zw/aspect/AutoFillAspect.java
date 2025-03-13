package com.zw.aspect;

import com.zw.annotations.AutoFill;
import com.zw.enums.OperationType;
import com.zw.util.UserInfoUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class AutoFillAspect {
    @Pointcut("execution(* com.zw.mapper.*.*(..)) && @annotation(com.zw.annotations.AutoFill)")
    public void autoFillPointcut() {
    }

    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object arg = args[0];
        Class<?> clazz = arg.getClass();
        if (OperationType.INSERT.equals(operationType)) {
            Method setCreateTime = clazz.getDeclaredMethod("setCreateTime", Date.class);
            Method setCreateBy = clazz.getDeclaredMethod("setCreateBy", Integer.class);
            setCreateTime.invoke(arg, new Date());
            setCreateBy.invoke(arg, UserInfoUtil.getCurrentUser().getId());
        }
        Method setEditTime = clazz.getDeclaredMethod("setEditTime", Date.class);
        Method setEditBy = clazz.getDeclaredMethod("setEditBy", Integer.class);
        setEditTime.invoke(arg, new Date());
        setEditBy.invoke(arg, UserInfoUtil.getCurrentUser().getId());
    }
}
