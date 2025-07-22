package com.life.decision.support.enums;

public enum UrlAccessType {
    VIDEO("1", "视频"),
    AUDIO("2", "音频");
    final String id;
    final String name;

    UrlAccessType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}