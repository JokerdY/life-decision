package com.life.decision.support.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionnaireGroupInformation {
    private String groupId;
    private String questionnaireId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String submitId;
    private String userId;
    private String questionnaireName;

    public QuestionnaireGroupInformation(String questionnaireId, String submitId, String userId, LocalDateTime createDate, LocalDateTime updateDate) {
        this.questionnaireId = questionnaireId;
        this.submitId = submitId;
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public QuestionnaireGroupInformation() {
    }
}
