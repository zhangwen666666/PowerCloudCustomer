package com.zw.util;

import com.zw.entity.TRole;
import com.zw.entity.TUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class UserInfoUtil {
    public static TUser getCurrentUser(){
        return ((TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    /**
     * 判断当前登录用户是否是管理员
     * @return
     */
    public static boolean isAdmin(){
        List<TRole> roleList = getCurrentUser().getRoleList();
        if (roleList == null || roleList.isEmpty()){
            return false;
        }
        for (TRole tRole : roleList) {
            if (tRole.getRole().equals("admin")) {
                // 是管理员
                return true;
            }
        }
        return false;
    }
}
