package com.zw.mapper;

import com.zw.entity.TTran;

import java.math.BigDecimal;

public interface TTranMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TTran record);

    int insertSelective(TTran record);

    TTran selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTran record);

    int updateByPrimaryKey(TTran record);

    /**
     * 查询交易金额
     * @param isSuccess true查询成功的，false查询失败的 null查询总的
     * @return
     */
    BigDecimal sumTranAmount(Boolean isSuccess);
}