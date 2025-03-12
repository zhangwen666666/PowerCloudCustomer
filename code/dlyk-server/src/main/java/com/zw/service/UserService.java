package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.dto.UserSaveDTO;
import com.zw.entity.TUser;
import com.zw.vo.UserDetailVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

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

    /**
     * 编辑用户
     * @param userSaveDTO
     * @return
     */
    int updateUser(UserSaveDTO userSaveDTO);

    /**
     * 根据id删除单个用户
     * @param id
     * @return
     */
    int deleteUser(Integer id);

    /**
     * 根据id批量删除用户
     * @param ids
     * @return
     */
    int batchDeleteUser(List<Integer> ids);
}
