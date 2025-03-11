package com.zw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.entity.TUser;
import com.zw.mapper.TUserMapper;
import com.zw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser user = tUserMapper.selectByLoginAct(username);
        if (user == null) {
            throw new UsernameNotFoundException("登录账号不存在");
        }
        return user;
    }


    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<TUser> userPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TUser> userList = tUserMapper.selectAll();
        return new PageInfo<>(userList);
    }


    /**
     * 根据id查询用户详情
     * @param id
     * @return
     */
    @Override
    public TUser userDetail(Integer id) {
        return tUserMapper.selectDetailById(id);
    }
}
