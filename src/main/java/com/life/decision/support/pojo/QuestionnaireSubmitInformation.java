package com.life.decision.support.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.life.decision.support.common.PageDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionnaireSubmitInformation extends PageDto {
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String questionnaireId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private QuestionnaireInformation questionnaireInformation;

    private UserInformation user;

    private Integer isAdminSubmit;

    private Integer questionnaireStatus;

    private String questionnaireStatusName;

    private String queryStartDate;

    private String queryEndDate;
}
