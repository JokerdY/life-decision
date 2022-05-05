package com.life.decision.support.dto;

import lombok.Data;

@Data
public class SubmitOfTheQuestionnaireGroup {
    private String questionnaireName;
    private String questionnaireId;
    private Boolean isSubmit;
    private Integer submitCount;
}
