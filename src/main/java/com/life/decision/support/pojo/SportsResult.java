package com.life.decision.support.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SportsResult {
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    /**
     * 运动前热身
     */
    private String warmUpBeforeExercise;
    /**
     * 具体运动
     */
    private String specificSports;
    /**
     * 运动后拉伸
     */
    private String stretchingAfterExercise;
    private String healthEducation;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rDate;
}
