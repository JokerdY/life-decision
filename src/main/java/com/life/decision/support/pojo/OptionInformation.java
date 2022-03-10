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
public class OptionInformation {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 问卷id
     */
    private String questionnaireId;

    /**
     * 问题id
     */
    private String questionId;

    /**
     * 选项值
     */
    private String optionValue;

    /**
     * 是否支持填空，如：其他__  0：不支持 1：支持
     */
    private Integer fillEnabled;

    private Integer optionSort;

    /**
     * 关联跳转id -1标识End
     */
    private String associatedJumpId;

}
