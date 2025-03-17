package com.zw.service;

import com.zw.entity.TProduct;

import java.util.List;

public interface ProductService {
    /**
     * 获取所有的商品信息列表
     * @return
     */
    List<TProduct> getAllProduct();

}
