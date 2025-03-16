package com.zw.mapper;

import com.zw.entity.TDicValue;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TDicValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TDicValue record);

    int insertSelective(TDicValue record);

    TDicValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TDicValue record);

    int updateByPrimaryKey(TDicValue record);

    @Select("select * from t_dic_value")
    List<TDicValue> selectAll();

    /**
     * 根据字典类型码查询字典值列表
     * @param typeCode
     * @return
     */
    @Select("select * from t_dic_value where type_code = #{typeCode}")
    List<TDicValue> selectByTypeCode(String typeCode);
}