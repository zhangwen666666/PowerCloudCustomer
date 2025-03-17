package com.zw.vo;

import com.zw.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVO extends TCustomer {
    private TClue clueDO = new TClue(); // 线索对象
    private TUser ownerDO = new TUser(); // 负责人对象
    private TActivity activityDO = new TActivity(); // 活动对象
    private TDicValue appellationDO = new TDicValue();// 称呼对象
    private TDicValue needLoanDO = new TDicValue();// 是否需要贷款对象
    private TDicValue intentionStateDO = new TDicValue();// 意向状态对象
    private TDicValue stateDO = new TDicValue(); // 线索状态对象
    private TDicValue sourceDO = new TDicValue(); // 线索来源对象
    private TProduct intentionProductDO = new TProduct(); // 意向产品对象
}
