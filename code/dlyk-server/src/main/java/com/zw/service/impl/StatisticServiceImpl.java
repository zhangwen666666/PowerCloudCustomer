package com.zw.service.impl;

import com.zw.manager.StatisticManager;
import com.zw.service.StatisticService;
import com.zw.vo.ChartStatisticDataVO;
import com.zw.vo.StatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 获取销售漏斗数据
     * @return
     */
    @Override
    public List<ChartStatisticDataVO> saleFunnelData() {
        return statisticManager.saleFunnelData();
    }

    /**
     * 获取客户来源饼图数据
     * @return
     */
    @Override
    public List<ChartStatisticDataVO> sourcePieData() {
        return statisticManager.sourcePieData();
    }
}
