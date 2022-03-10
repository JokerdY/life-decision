package com.life.decision.support.pojo;
import lombok.Data;

@Data
public class QuestionnaireSubmitInformation{
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String questionnaireId;

    private String createTime;

    private QuestionnaireInformation questionnaireInformation;

    private UserInformation userInformation;
}
