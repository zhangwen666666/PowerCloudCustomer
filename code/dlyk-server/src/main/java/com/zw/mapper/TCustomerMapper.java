package com.zw.mapper;

import com.zw.annotations.AutoFill;
import com.zw.annotations.DataScope;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TCustomer;
import com.zw.enums.OperationType;
import com.zw.vo.CustomerExcelVO;
import com.zw.vo.CustomerVO;

import java.util.List;

public interface TCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TCustomer record);

    @AutoFill(OperationType.INSERT)
    int insertSelective(TCustomer record);

    TCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCustomer record);

    int updateByPrimaryKey(TCustomer record);

    /**
     * 分页查询客户数据
     * @param filterSqlDTO
     * @return
     */
    @DataScope(tableAlias = "tc",tableFiled = "create_by")
    List<CustomerVO> selectAll(FilterSqlDTO filterSqlDTO);

    @DataScope(tableAlias = "tc",tableFiled = "create_by")
    List<CustomerExcelVO> selectAllExcel(FilterSqlDTO filterSqlDTO);
}