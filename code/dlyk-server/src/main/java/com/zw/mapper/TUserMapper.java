package com.zw.mapper;

import com.zw.entity.TUser;

import java.util.List;

public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    /**
     * 根据登录账号查询用户
     * @param loginAct 登录账号
     * @return
     */
    TUser selectByLoginAct(String loginAct);

    /**
     * 查询所有账号
     * @return
     */
    List<TUser> selectAll();


    /**
     * 根据id查询用户详情，包括修改人姓名和创建人姓名
     * @param id
     * @return
     */
    TUser selectDetailById(Integer id);
}