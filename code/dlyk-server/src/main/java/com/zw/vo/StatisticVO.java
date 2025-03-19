package com.zw.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticVO {
    // 有效的市场活动总数
    private Integer effectiveActivityCount;

    // 总市场活动总数
    private Integer totalActivityCount;

    // 线索总数
    private Integer totalClueCount;

    // 客户总数
    private Integer totalCustomerCount;

    // 成功的交易额
    private BigDecimal successTranAmount;

    // 总的交易额
    private BigDecimal totalTranAmount;
}
