package com.zw.service.impl;

import com.zw.entity.TDicValue;
import com.zw.mapper.TDicValueMapper;
import com.zw.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private TDicValueMapper tDicValueMapper;
    /**
     * 根据字典类型码查询字典值列表
     * @param typeCode
     * @return
     */
    @Override
    public List<TDicValue> dicData(String typeCode) {
        return tDicValueMapper.selectByTypeCode(typeCode);
    }
}
