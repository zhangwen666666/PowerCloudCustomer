package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TCustomer;
import com.zw.result.R;
import com.zw.service.CustomerService;
import com.zw.vo.CustomerVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * 线索转换为客户
     * @param tCustomer
     * @return
     */
    @PostMapping("/api/customer/clueToCustomer")
    public R clueToCustomer(@RequestBody TCustomer tCustomer) {
        // System.out.println(tCustomer);
        customerService.clueToCustomer(tCustomer);
        return R.OK();
    }

    /**
     * 分页查询客户数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/api/customer/list")
    public R<PageInfo<CustomerVO>> customerList(Integer pageNum, Integer pageSize) {
        PageInfo<CustomerVO> pageInfo = customerService.customerList(pageNum, pageSize);
        return R.OK(pageInfo);
    }


    /**
     * 导入Excel
     */
    @GetMapping("/api/exportExcel")
    public void exportExcel(HttpServletResponse response) throws Exception{
        // 设置相应的内容类型是二进制流的形式
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        // 设置响应头 (告诉浏览器相应的是一个attachment附件，文件名是customer.xlsx)
        response.setHeader("Content-Disposition", "attachment;filename=customer.xlsx");
        customerService.exportExcel(response);
    }
}
