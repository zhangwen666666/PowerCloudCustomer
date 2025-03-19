package com.zw.manager;

import com.zw.mapper.TActivityMapper;
import com.zw.mapper.TClueMapper;
import com.zw.mapper.TCustomerMapper;
import com.zw.mapper.TTranMapper;
import com.zw.vo.StatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StatisticManager {
    @Autowired
    private TActivityMapper tActivityMapper;
    @Autowired
    private TClueMapper tClueMapper;
    @Autowired
    private TCustomerMapper tCustomerMapper;
    @Autowired
    private TTranMapper tTranMapper;

    /**
     * 查询统计数据
     * @return
     */
    public StatisticVO loadSummaryData() {
        int effectiveActivityCount = tActivityMapper.selectCount(true);
        int totalActivityCount = tActivityMapper.selectCount(null);
        int totalClueCount = tClueMapper.selectCount();
        int totalCustomerCount = tCustomerMapper.selectCount();
        BigDecimal successTranAmount = tTranMapper.sumTranAmount(true);
        BigDecimal totalTranAmount = tTranMapper.sumTranAmount(null);
        return StatisticVO.builder()
                .effectiveActivityCount(effectiveActivityCount)
                .totalActivityCount(totalActivityCount)
                .totalClueCount(totalClueCount)
                .totalCustomerCount(totalCustomerCount)
                .successTranAmount(successTranAmount)
                .totalTranAmount(totalTranAmount)
                .build();
    }
}
