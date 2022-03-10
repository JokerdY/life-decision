package com.life.decision.support.pojo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Data
public class QuestionAnswer {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String questionnaireId;

    private String questionId;

    private LocalDateTime createDate;

    /**
     * 问题选项id
     */
    private String optionId;

    /**
     * 填空或备注信息
     */
    private String comment;
}
