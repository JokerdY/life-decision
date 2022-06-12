package com.life.decision.support.pojo;

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
public class QuestionInformation{

    private static final long serialVersionUID = 1L;

    private String id;

    private String questionnaireId;

    /**
     * sort
     */
    private Integer questionSort;

    /**
     * 问题名称{}表示填空的位置，由前端进行渲染
     */
    private String questionName;

    /**
     * 大标签名
     */
    private String bigLabelId;

    /**
     * 问题类型：1-填空 2-单选 3-多选 4-时间计数器 5-天数计数器
     */
    private Integer questionType;

    private Integer requiredEnabled;

}
