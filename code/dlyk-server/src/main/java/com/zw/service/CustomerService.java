package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TCustomer;
import com.zw.vo.CustomerVO;
import jakarta.servlet.http.HttpServletResponse;

public interface CustomerService {
    /**
     * 线索转客户
     * @param tCustomer
     */
    void clueToCustomer(TCustomer tCustomer);

    /**
     * 分页查询客户数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<CustomerVO> customerList(Integer pageNum, Integer pageSize);

    /**
     * 导出客户信息到Excel文件中
     */
    void exportExcel(HttpServletResponse response) throws Exception;

}
