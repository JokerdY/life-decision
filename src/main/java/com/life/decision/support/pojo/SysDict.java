package com.life.decision.support.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Data
public class SysDict {

    private static final long serialVersionUID = 1L;

    private String id;

    private String tableName;

    /**
     * 字典值
     */
    private Integer columnValue;

    /**
     * 对应标签名
     */
    private String convertName;

    /**
     * 排序
     */
    private Integer sortId;

    private String columnName;
}
