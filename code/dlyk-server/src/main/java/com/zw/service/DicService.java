package com.zw.service;

import com.zw.entity.TDicValue;

import java.util.List;

public interface DicService {
    /**
     * 根据字典类型码查询字典值列表
     * @param typeCode
     * @return
     */
    List<TDicValue> dicData(String typeCode);
}
