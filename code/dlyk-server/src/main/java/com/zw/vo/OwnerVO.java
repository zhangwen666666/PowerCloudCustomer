package com.zw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerVO {
    // 负责人id
    private Integer ownerId;
    // 负责人名称
    private String ownerName;
}
