package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<TUser> userPage(Integer pageNum, Integer pageSize);

    /**
     * 根据id查询用户详情
     * @param id
     * @return
     */
    TUser userDetail(Integer id);
}
