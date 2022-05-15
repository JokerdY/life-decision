package com.life.decision.support.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.life.decision.support.pojo.QuestionnaireInformation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionnaireInformationUserDto extends QuestionnaireInformation {
    private String userId;
    private Boolean submitEnabled;
    /**
     * 填写时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fillDate;
    private Integer submitCount;
    private String searchContent;
}
