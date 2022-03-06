package com.life.decision.support.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@Builder
public class QuestionInformation extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String questionnaireId;

    /**
     * 问题序号 如1 1.1
     */
    private Double questionNum;

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

}
