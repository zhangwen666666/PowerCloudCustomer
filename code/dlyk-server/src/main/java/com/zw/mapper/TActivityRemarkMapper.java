package com.zw.mapper;

import com.zw.annotations.AutoFill;
import com.zw.annotations.DataScope;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TActivityRemark;
import com.zw.enums.OperationType;
import com.zw.vo.ActivityRemarkVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TActivityRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    @AutoFill(OperationType.INSERT)
    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);

    /**
     * 查询你活动备注数据
     * @param filterSqlDTO
     * @param activityId
     * @return
     */
    @DataScope(tableAlias = "tar",tableFiled = "create_by")
    List<ActivityRemarkVO> selectAll(
            @Param("filterSqlDTO") FilterSqlDTO filterSqlDTO,
            @Param("activityId") Integer activityId);
}