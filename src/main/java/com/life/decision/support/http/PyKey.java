package com.life.decision.support.http;

public enum PyKey {
    DIET("Diet"),
    SPORTS("Sports"),
    RECIPE("食谱"),
    BREAKFAST("早餐"),
    LUNCH("午餐"),
    DINNER("晚餐");

    private final String key;

    PyKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
