package com.life.decision.support.dto;

import lombok.Data;

@Data
public class UserInHomeVo extends UserInformationDto {
    private String bloodPressure;
    private String fillInTheDate;
    private String bloodPressureStr;
    // diff为null则不展示
    private String BMIDiff;
    private String weightDiff;
    private String bodyFatPercentage;
}
