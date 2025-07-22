package com.life.decision.support.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PsychologyResult {
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String result;
    private String advice;
    private String healthEducation;
    private String groupId;
    private String physical;
    private String queryStartCreateDate;
    private String queryEndCreateDate;
}
