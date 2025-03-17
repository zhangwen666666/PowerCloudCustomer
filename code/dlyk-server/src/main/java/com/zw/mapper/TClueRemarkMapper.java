package com.zw.mapper;

import com.zw.annotations.AutoFill;
import com.zw.annotations.DataScope;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TClueRemark;
import com.zw.enums.OperationType;
import com.zw.vo.ClueRemarkVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TClueRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    @AutoFill(OperationType.INSERT)
    int insert(TClueRemark record);

    int insertSelective(TClueRemark record);

    TClueRemark selectByPrimaryKey(Integer id);

    @AutoFill(OperationType.UPDATE)
    int updateByPrimaryKeySelective(TClueRemark record);

    int updateByPrimaryKey(TClueRemark record);

    /**
     * 分页查询线索备注信息
     * @param filterSqlDTO
     * @param clueId
     * @return
     */
    @DataScope(tableAlias = "tcr",tableFiled = "create_by")
    List<ClueRemarkVO> selectAll(
            @Param("filterSqlDTO") FilterSqlDTO filterSqlDTO,
            @Param("clueId") Integer clueId);
}