package com.life.decision.support.enums;

public enum ModuleType {
    RECIPE("1", "营养食谱"),
    SPORTS("2", "元气运动"),
    PSYCHOLOGY("3", "心灵空间"),
    MEDICINE("4", "中医保健"),
    ACCESS("-1", "访问");

    ModuleType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    final String name;
    final String type;
}
