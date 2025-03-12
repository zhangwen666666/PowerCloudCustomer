package com.zw.mapper;

import com.zw.entity.TRole;

import java.util.List;

public interface TRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TRole record);

    int insertSelective(TRole record);

    TRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    List<TRole> selectByUserId(Integer userId);
}