package com.zw.mapper;

import com.zw.annotations.DataScope;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TActivity;
import com.zw.vo.ActivityVO;
import com.zw.vo.OwnerVO;

import java.util.List;

public interface TActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TActivity record);

    int insertSelective(TActivity record);

    TActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivity record);

    int updateByPrimaryKey(TActivity record);

    /**
     * 分页查询市场活动数据
     * @param filterSqlDTO 过滤条件 自动生成
     * @return
     */
    @DataScope(tableAlias = "ta",tableFiled = "owner_id")
    List<ActivityVO> selectAll(FilterSqlDTO filterSqlDTO);

    /**
     * 获取负责人列表
     * @return
     */
    @DataScope(tableAlias = "ta",tableFiled = "owner_id")
    List<OwnerVO> getOwnerList(FilterSqlDTO filterSqlDTO);

}