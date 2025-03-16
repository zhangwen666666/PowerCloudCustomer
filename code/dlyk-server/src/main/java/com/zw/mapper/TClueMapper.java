package com.zw.mapper;

import com.zw.dto.CluePageQueryDTO;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TClue;

import java.util.List;

public interface TClueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TClue record);

    int insertSelective(TClue record);

    TClue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClue record);

    int updateByPrimaryKey(TClue record);

    /**
     * 查询所有线索数据
     * @param filterSqlDTO
     * @return
     */
    List<CluePageQueryDTO> selectAll(FilterSqlDTO filterSqlDTO);

    /**
     * 将列表中的数据存储到数据库中
     * @param cachedDataList
     */
    void saveClueList(List<TClue> cachedDataList);

    /**
     * 根据id集合批量删除线索
     * @param ids
     */
    void batchDelete(List<Integer> ids);
}