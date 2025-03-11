package com.zw.util;

import com.zw.entity.TUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserInfoUtil {
    public static TUser getCurrentUser(){
        return ((TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
