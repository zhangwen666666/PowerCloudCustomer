package com.zw.mapper;

import com.zw.annotations.AutoFill;
import com.zw.annotations.DataScope;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TUser;
import com.zw.enums.OperationType;
import com.zw.vo.UserDetailVO;

import java.util.List;

public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    @AutoFill(OperationType.INSERT)
    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    @AutoFill(OperationType.UPDATE)
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
    @DataScope(tableFiled = "id")
    List<TUser> selectAll(FilterSqlDTO filterSqlDTO);


    /**
     * 根据id查询用户详情，包括修改人姓名和创建人姓名
     * @param id
     * @return
     */
    UserDetailVO selectDetailById(Integer id);

    /**
     * 根据id批量删除用户
     * @param ids
     * @return
     */
    int deleteByIds(List<Integer> ids);
}