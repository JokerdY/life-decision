package com.life.decision.support.pojo;

import lombok.Data;

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

    private Integer telphoneNum;

    private String password;

    /**
     * 0:admin 1:user
     */
    private Integer adminEnable;
}
