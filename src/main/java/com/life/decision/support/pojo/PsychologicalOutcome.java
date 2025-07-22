package com.life.decision.support.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PsychologicalOutcome {
    private String id;
    private String userId;
    private String anxiety;
    private String depression;
    private String pressure;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
