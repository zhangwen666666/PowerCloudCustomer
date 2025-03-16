package com.zw.task;

import com.zw.entity.TDicValue;
import com.zw.entity.TProduct;
import com.zw.mapper.TDicValueMapper;
import com.zw.mapper.TProductMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataTask {
    @Autowired
    private TDicValueMapper tDicValueMapper;
    @Autowired
    private TProductMapper tProductMapper;

    public static Map<String,Integer> dicCacheMap;
    public static Map<String,Integer> productCacheMap;

    // 在Spring容器初始化完成后立即执行
    @PostConstruct
    public void executeOnStartup() {
        task();
    }

    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行一次
    public void task(){
        log.info("定时任务开始执行,加载数据");
        List<TDicValue> tDicValueList = tDicValueMapper.selectAll();
        dicCacheMap = tDicValueList.stream().collect(Collectors.toMap(TDicValue::getTypeValue, TDicValue::getId));
        List<TProduct> tProductList = tProductMapper.selectAll();
        productCacheMap = tProductList.stream().collect(Collectors.toMap(TProduct::getName, TProduct::getId));
    }
}
