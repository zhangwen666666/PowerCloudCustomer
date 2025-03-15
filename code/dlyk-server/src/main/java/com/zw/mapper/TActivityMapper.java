package com.zw.mapper;

import com.zw.annotations.AutoFill;
import com.zw.annotations.DataScope;
import com.zw.dto.ActivityPageQueryDTO;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TActivity;
import com.zw.enums.OperationType;
import com.zw.vo.ActivityVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TActivityMapper {
    int deleteByPrimaryKey(Integer id);

    @AutoFill(OperationType.INSERT)
    int insert(TActivity record);

    int insertSelective(TActivity record);

    TActivity selectByPrimaryKey(Integer id);

    @AutoFill(OperationType.UPDATE)
    int updateByPrimaryKeySelective(TActivity record);

    int updateByPrimaryKey(TActivity record);

    /**
     * 分页查询市场活动数据
     * @param filterSqlDTO 过滤条件 自动生成
     * @return
     */
    @DataScope(tableAlias = "ta",tableFiled = "owner_id")
    List<ActivityVO> selectAll(@Param("filterSqlDTO") FilterSqlDTO filterSqlDTO,@Param("activityPageQueryDTO") ActivityPageQueryDTO activityPageQueryDTO);

    /**
     * 根据id查询活动详细数据
     * @param id
     * @return
     */
    ActivityVO selectDetailById(Integer id);

    /**
     * 根据id集合批量删除市场活动数据
     * @param ids
     */
    void deleteBatchByIds(List<Integer> ids);
}