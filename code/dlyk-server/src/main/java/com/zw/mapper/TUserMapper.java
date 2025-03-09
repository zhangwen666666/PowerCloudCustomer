package com.zw.mapper;

import com.zw.entity.TUser;

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
}