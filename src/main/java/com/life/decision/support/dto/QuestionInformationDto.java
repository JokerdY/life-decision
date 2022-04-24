package com.life.decision.support.dto;

import com.life.decision.support.pojo.OptionInformation;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionInformation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionInformationDto extends QuestionInformation {

    private String bigLabel;

    private List<OptionInformation> optionInformationList;

    private QuestionAnswer questionAnswer;

    private List<QuestionInformationDto> child;
}
