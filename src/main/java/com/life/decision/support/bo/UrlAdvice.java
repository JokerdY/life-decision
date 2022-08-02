package com.life.decision.support.bo;

import lombok.Data;

@Data
public class UrlAdvice {
    private String name;
    private String time;
    /**
     * url可能为空
     */
    private String url;

    public UrlAdvice(String name, String time, String url) {
        this.name = name;
        this.time = time;
        this.url = url;
    }
}
