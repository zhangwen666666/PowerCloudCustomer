package com.zw.vo;

import com.zw.entity.TActivity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ActivityVO extends TActivity {
    private String ownerName;
    private String createrName;
    private String editorName;
}
