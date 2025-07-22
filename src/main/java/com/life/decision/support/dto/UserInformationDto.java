package com.life.decision.support.dto;

import com.life.decision.support.common.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInformationDto extends PageDto {

    private String id;

    private String userId;

    private String userName;

    private String telphoneNum;

    private String password;

    /**
     * 1:admin 0:user
     */
    private Integer adminEnable;

    /**
     * 0:female 1:male
     */
    private Integer sex;

    private Date dateOfBirth;

    private String height;

    private String weight;

    /**
     * 教育程度
     */
    private Integer educationalLevel;

    /**
     * 职业
     */
    private Integer occupation;

    private Integer marital;

    private Integer householdIncome;
    /**
     * 身份
     */
    private String identity;

    private String BMI;

    private String educationalLevelDto;

    private String occupationDto;

    private String sexDto;

    private String maritalDto;

    private String householdIncomeDto;

    private String age;

    private String queryStartDateStart;

    private String queryStartDateEnd;

    public String getAge() {
        LocalDate localDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        return localDate.until(now).getYears() + "";
    }
}
