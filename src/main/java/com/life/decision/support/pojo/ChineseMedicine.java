package com.life.decision.support.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChineseMedicine {
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    /**
     * 穴位
     */
    private String acupressure;
    /**
     * 五行
     */
    private String fiveElementsMusic;
    private String groupId;
}
