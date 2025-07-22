package com.life.decision.support.vo;

import lombok.Data;

@Data
public class UrlAccessCountVo {
    private String id;
    private String urlName;
    private String url;
    private Integer count;
    private String type;
    private String typeName;
}
