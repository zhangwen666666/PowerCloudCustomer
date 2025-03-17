package com.zw.vo;

import com.zw.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClueVO extends TClue {
    private TUser ownerDO = new TUser(); // 线索所属人
    private TActivity activityDO = new TActivity(); // 关联的活动
    private TDicValue appellationDO = new TDicValue(); // 称呼
    private TDicValue needLoanDO = new TDicValue(); // 是否需要贷款
    private TDicValue intentionStateDO = new TDicValue(); // 意向状态
    private TProduct intentionProductDO = new TProduct(); // 意向产品
    private TDicValue stateDO = new TDicValue(); // 线索状态
    private TDicValue sourceDO = new TDicValue(); // 线索来源
}
