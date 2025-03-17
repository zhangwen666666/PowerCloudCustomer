package com.zw.service.impl;

import com.zw.entity.TProduct;
import com.zw.mapper.TProductMapper;
import com.zw.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private TProductMapper tProductMapper;
    /**
     * 获取所有的商品信息列表
     * @return
     */
    @Override
    public List<TProduct> getAllProduct() {
        return tProductMapper.selectAll();
    }
}
