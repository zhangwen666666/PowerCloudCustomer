package com.zw.mapper;

import com.zw.entity.TProduct;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TProduct record);

    int insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TProduct record);

    int updateByPrimaryKey(TProduct record);

    @Select("select * from t_product")
    List<TProduct> selectAll();

}