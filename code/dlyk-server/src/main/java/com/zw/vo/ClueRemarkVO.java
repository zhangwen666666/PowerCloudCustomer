package com.zw.vo;

import com.zw.entity.TClueRemark;
import com.zw.entity.TDicValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClueRemarkVO extends TClueRemark {
    private String createrName;
    private String editorName;
    private TDicValue noteWayDO = new TDicValue();
}
