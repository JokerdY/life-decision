package com.life.decision.support.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@Builder
public class QuestionAnswer extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
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
