package com.life.decision.support.enums;

/**
 * @author Joker
 * @since 2020-12-22
 * 返回结果字段
 */
public enum ResultEnum {
    /**
     * 状态字段
     */
    STATUS("status"),
    /**
     * 数据字段
     */
    DATA("data"),
    /**
     * 分页总数
     */
    TOTAL("total"),
    /**
     * 错误信息字段
     */
    MESSAGE("message");

    private String msg;

    ResultEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
