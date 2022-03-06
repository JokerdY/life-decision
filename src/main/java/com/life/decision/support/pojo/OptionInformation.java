package com.life.decision.support.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("option_information")
public class OptionInformation extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
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
