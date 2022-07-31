package com.life.decision.support.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RecipeResult {
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rDate;
    private String breakFast;
    private String lunch;
    private String dinner;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    /**
     * 膳食建议
     */
    private String dietaryAdvice;
    /**
     * 总热量
     */
    private String totalCalories;
    private String healthEducation;
}
