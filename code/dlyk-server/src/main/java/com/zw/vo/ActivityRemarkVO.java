package com.zw.vo;

import com.zw.entity.TActivityRemark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRemarkVO extends TActivityRemark {
    private String createrName;
    private String editorName;
}
