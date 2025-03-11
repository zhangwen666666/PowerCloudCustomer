package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.dto.UserSaveDTO;
import com.zw.entity.TUser;
import com.zw.vo.UserDetailVO;
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
    UserDetailVO userDetail(Integer id);

    /**
     * 新增用户
     * @param userSaveDTO
     * @return
     */
    int saveUser(UserSaveDTO userSaveDTO);
}
