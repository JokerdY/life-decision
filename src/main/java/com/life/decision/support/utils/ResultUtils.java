package com.life.decision.support.utils;

import com.life.decision.support.enums.ResultEnum;
import com.life.decision.support.enums.ReturnCodeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yxh
 * @description 封装返回结果的工具类
 * @since 2020-12-20
 */
public class ResultUtils {

    public static Map<String, Object> returnSuccess(Object data) {
        Map<String, Object> result = new HashMap<>(2);
        result.put(ResultEnum.DATA.getMsg(), data);
        result.put(ResultEnum.STATUS.getMsg(), ReturnCodeEnum.SUCCESS.getStatus());
        return result;
    }

    public static Map<String, Object> returnSuccess() {
        return returnSuccess("");
    }

    public static Map<String, Object> returnError(String msg) {
        Map<String, Object> result = new HashMap<>(2);
        result.put(ResultEnum.MESSAGE.getMsg(), msg);
        result.put(ResultEnum.STATUS.getMsg(), ReturnCodeEnum.ERROR.getStatus());
        return result;
    }

    public static Map<String, Object> returnError() {
        return returnError("");
    }

    public static Map<String, Object> returnPage(Object data) {
        Map<String, Object> result = new HashMap<>(4);
        result.put(ResultEnum.PAGE.getMsg(), data);
        result.put(ResultEnum.STATUS.getMsg(), ReturnCodeEnum.SUCCESS.getStatus());
        return result;
    }
}
