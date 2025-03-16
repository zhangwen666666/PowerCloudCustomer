package com.zw.web;

import com.zw.entity.TDicValue;
import com.zw.result.R;
import com.zw.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DicController {
    @Autowired
    private DicService dicService;
    /**
     * 根据字典类型码获取字典值集合
     * @param typeCode
     * @return
     */
    @GetMapping("/api/dicvalue/{typeCode}")
    public R<List<TDicValue>> dicData(@PathVariable String typeCode){
        return R.OK(dicService.dicData(typeCode));
    }
}
