package com.life.decision.support.pojo;

import lombok.Data;

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

}
