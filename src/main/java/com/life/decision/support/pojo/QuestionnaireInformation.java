package com.life.decision.support.pojo;

import com.life.decision.support.common.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionnaireInformation extends PageDto {

    private static final long serialVersionUID = 1L;

    private String id;

    private String questionnaireDescription;

    private Date createDate;

    private Date updateDate;

    /**
     * 1-逻辑删除 0-未删除
     */
    private Integer delFlag;

    /**
     * 问卷类型：1-健康 2-饮食 3-生活
     */
    private Integer questionnaireType;

    private String questionnaireTypeLabel;

    private String comment;

}
