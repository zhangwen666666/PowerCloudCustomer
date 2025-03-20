package com.zw.web;

import com.zw.result.R;
import com.zw.service.StatisticService;
import com.zw.vo.ChartStatisticDataVO;
import com.zw.vo.StatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    /**
     * 获取统计数据
     * @return
     */
    @GetMapping("/api/summary/data")
    public R<StatisticVO> summaryData(){
        StatisticVO statisticVO = statisticService.summaryData();
        return R.OK(statisticVO);
    }

    /**\
     * 获取销售漏斗数据
     * @return
     */
    @GetMapping("/api/saleFunnel/data")
    public R<List<ChartStatisticDataVO>> saleFunnelData(){
        List<ChartStatisticDataVO> list = statisticService.saleFunnelData();
        return R.OK(list);
    }

    /**
     * 获取线索来源饼图数据
     * @return
     */
    @GetMapping("/api/sourcePie/data")
    public R<List<ChartStatisticDataVO>> sourcePieData(){
        List<ChartStatisticDataVO> list = statisticService.sourcePieData();
        return R.OK(list);
    }
}
