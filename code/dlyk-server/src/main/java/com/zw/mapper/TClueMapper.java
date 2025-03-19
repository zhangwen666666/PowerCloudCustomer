package com.zw.mapper;

import com.zw.annotations.AutoFill;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TClue;
import com.zw.enums.OperationType;
import com.zw.vo.ClueVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TClueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TClue record);

    @AutoFill(OperationType.INSERT)
    int insertSelective(TClue record);

    TClue selectByPrimaryKey(Integer id);

    @AutoFill(OperationType.UPDATE)
    int updateByPrimaryKeySelective(TClue record);

    int updateByPrimaryKey(TClue record);

    /**
     * 查询所有线索数据
     * @param filterSqlDTO
     * @return
     */
    List<ClueVO> selectAll(FilterSqlDTO filterSqlDTO);

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

    /**
     * 根据手机号查询线索数量
     * @param phone
     * @return
     */
    @Select("select count(*) from t_clue where phone = #{phone}")
    int selectByCount(String phone);


    ClueVO detailByPrimaryKey(Integer id);

    @Select("select count(0) from t_clue")
    int selectCount();
}