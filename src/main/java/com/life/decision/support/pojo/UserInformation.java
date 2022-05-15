package com.life.decision.support.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Data
public class UserInformation {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userName;

    private String userId;

    private String telphoneNum;

    private String password;

    /**
     * 0:user 1:admin
     */
    private Integer adminEnable;

    /**
     * 0:female 1:male
     */
    private Integer sex;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    /**
     * 身高
     */
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
     * 婚姻状况
     */
    private Integer maritalStatus;

    /**
     * 家庭收入
     */
    private Integer householdIncome;
}
