package com.zw.service;

import com.zw.vo.ChartStatisticDataVO;
import com.zw.vo.StatisticVO;

import java.util.List;

public interface StatisticService {
    /**
     * 获取统计数据
     * @return
     */
    StatisticVO summaryData();

    /**
     * 获取销售漏斗数据
     * @return
     */
    List<ChartStatisticDataVO> saleFunnelData();

    /**
     * 获取客户来源饼图数据
     * @return
     */
    List<ChartStatisticDataVO> sourcePieData();
}
