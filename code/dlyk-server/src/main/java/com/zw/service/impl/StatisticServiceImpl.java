package com.zw.service.impl;

import com.zw.manager.StatisticManager;
import com.zw.service.StatisticService;
import com.zw.vo.StatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticManager statisticManager;

    /**
     * 获取统计数据
     * @return
     */
    @Override
    public StatisticVO summaryData() {
        return statisticManager.loadSummaryData();
    }
}
