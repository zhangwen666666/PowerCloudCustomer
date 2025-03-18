package com.zw.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TClue;
import com.zw.entity.TCustomer;
import com.zw.mapper.TClueMapper;
import com.zw.mapper.TCustomerMapper;
import com.zw.service.CustomerService;
import com.zw.vo.CustomerExcelVO;
import com.zw.vo.CustomerVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private TCustomerMapper tCustomerMapper;
    @Autowired
    private TClueMapper tClueMapper;

    /**
     * 线索转客户
     * 1. 向客户表插入一条数据
     * 2. 将线索状态改为已转换
     * @param tCustomer
     */
    @Override
    @Transactional
    public void clueToCustomer(TCustomer tCustomer) {
        // 查询线索，判断其是否已经转为客户了
        TClue tClue = tClueMapper.selectByPrimaryKey(tCustomer.getClueId());
        if (tClue.getState().equals(-1)) {
            // 已经转过了
            throw new RuntimeException("该线索已经转为客户了");
        }
        // 插入客户表
        tCustomerMapper.insertSelective(tCustomer);
        // 将线索状态改为已转换
        tClue.setState(-1);
        tClueMapper.updateByPrimaryKeySelective(tClue);
    }


    /**
     * 分页查询客户数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<CustomerVO> customerList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CustomerVO> customerList = tCustomerMapper.selectAll(new FilterSqlDTO());
        return new PageInfo<>(customerList);
    }

    /**
     * 导出客户信息到Excel文件中
     */
    @Override
    public void exportExcel(HttpServletResponse response, List<Integer> ids) throws Exception{
        List<CustomerExcelVO> dataList = tCustomerMapper.selectAllExcel(new FilterSqlDTO(), ids);
        // 写入Excel文件
        EasyExcel.write(response.getOutputStream(), CustomerExcelVO.class)
                .sheet().doWrite(dataList);
    }
}
