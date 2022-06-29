package com.life.decision.support.dto;

import lombok.Data;

@Data
public class UserInHomeVo extends UserInformationDto {
    private String bloodPressure;
    private String fillInTheDate;
    private String bloodPressureStr;
    private String weightDiff;
    private String BMI;
    private String BMIDiff;
    private String bodyFatPercentage;
    private String bodyFatPercentageDiff;
}
