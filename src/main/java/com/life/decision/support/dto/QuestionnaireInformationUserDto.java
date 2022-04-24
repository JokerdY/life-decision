package com.life.decision.support.dto;

import com.life.decision.support.pojo.QuestionnaireInformation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionnaireInformationUserDto extends QuestionnaireInformation {
    private String userId;
    private Boolean submitEnabled;
    private Date createTime;
}
