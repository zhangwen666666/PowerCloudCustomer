package com.zw.web;

import com.zw.result.R;
import com.zw.service.StatisticService;
import com.zw.vo.StatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
