package com.life.decision.support.common;

public enum URL {
    PY_URL("127.0.0.1:8000");

    private final String url;

    URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
