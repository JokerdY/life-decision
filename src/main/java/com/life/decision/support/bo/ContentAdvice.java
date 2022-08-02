package com.life.decision.support.bo;

import lombok.Data;

@Data
    public class ContentAdvice {
    private String name;
    private String content;

    public ContentAdvice(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
