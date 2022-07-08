package com.life.decision.support.result;

import com.life.decision.support.pojo.OptionInformation;
import com.life.decision.support.pojo.QuestionAnswer;
import lombok.Data;

import java.util.List;

@Data
public class QuestionInformationResultDto {
    private static final long serialVersionUID = 1L;
    private List<OptionInformation> optionInformationList;
    private QuestionAnswer questionAnswer;
    private String questionnaireId;
    private String id;
    private String questionName;
    private Integer requiredEnabled;
}
