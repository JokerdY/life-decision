package com.life.decision.support.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class QuestionnaireInformation extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String questionnaireDescription;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    /**
     * 1-逻辑删除 0-未删除
     */
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;

    /**
     * 问卷类型：1-健康 2-饮食 3-生活
     */
    private Integer questionnaireType;

}
