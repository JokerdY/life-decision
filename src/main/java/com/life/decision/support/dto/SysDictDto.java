package com.life.decision.support.dto;

import lombok.Data;

@Data
public class SysDictDto {
    // 标签名
    private String label;
    // 实际描述
    private String description;
    // 数据值
    private String value;
    // 序号
    private Integer sortId;
    private String id;
}
