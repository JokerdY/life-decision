package com.life.decision.support.dto;

import com.life.decision.support.pojo.QuestionAnswer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerDto extends QuestionAnswer {
    private String optionValue;
    private String fillEnabled;
}
