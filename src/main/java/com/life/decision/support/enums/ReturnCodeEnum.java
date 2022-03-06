package com.life.decision.support.enums;

/**
 * @author Joker
 * @since 2020-12-21
 * 返回状态码
 */
public enum ReturnCodeEnum {
    /**
     * 成功标识
     */
    SUCCESS("success"),
    /**
     * 失败标识
     */
    ERROR("error");

    /**
     * 返回状态
     */
    private String status;

    ReturnCodeEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
