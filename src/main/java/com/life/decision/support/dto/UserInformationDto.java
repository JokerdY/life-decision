package com.life.decision.support.dto;

import com.life.decision.support.common.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInformationDto extends PageDto {

    private String id;

    private String userName;

    private String telphoneNum;

    private String password;

    /**
     * 0:admin 1:user
     */
    private Integer adminEnable;

    /**
     * 0:female 1:male
     */
    private Integer sex;

    private Date dateOfBirth;

    private Double height;

    private Double weight;

    /**
     * 教育程度
     */
    private Integer educationalLevel;

    /**
     * 职业
     */
    private Integer occupation;

    /**
     * 身份
     */
    private String identity;

    private String BMI;

    private String educationalLevelDto;

    private String occupationDto;

    private String sexDto;
}
