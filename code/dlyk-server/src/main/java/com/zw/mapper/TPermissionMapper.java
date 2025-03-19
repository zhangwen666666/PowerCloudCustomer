package com.zw.mapper;

import com.zw.entity.TPermission;

import java.util.List;

public interface TPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    TPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TPermission record);

    int updateByPrimaryKey(TPermission record);

    /**
     * 根据用户id查询菜单权限
     * @param userId
     * @return
     */
    List<TPermission> selectMenuPermissionByUserId(Integer userId);


    /**
     * 根据用户id查询功能权限
     * @param userId
     * @return
     */
    List<TPermission> selectButtonPermissionByUserId(Integer userId);
}