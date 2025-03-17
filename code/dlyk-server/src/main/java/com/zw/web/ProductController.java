package com.zw.web;

import com.zw.entity.TProduct;
import com.zw.result.R;
import com.zw.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 查询所有的产品信息
     * @return
     */
    @GetMapping("/api/product")
    public R<List<TProduct>> product() {
        List<TProduct> products = productService.getAllProduct();
        return R.OK(products);
    }
}
