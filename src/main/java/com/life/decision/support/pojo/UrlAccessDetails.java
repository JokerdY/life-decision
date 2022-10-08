package com.life.decision.support.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UrlAccessDetails {
    private String id;
    private String urlName;
    private String userId;
    private Date createDate;
    private String url;
    /**
     * 1 视频 2 音频
     */
    private String type;
}
