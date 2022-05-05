package com.life.decision.support.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionnaireSubmitInformation {
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String questionnaireId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private QuestionnaireInformation questionnaireInformation;

    private UserInformation user;

    private Integer isAdmitSubmit;
}
