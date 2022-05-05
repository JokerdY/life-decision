package com.life.decision.support.pojo;

import lombok.Data;

@Data
public class PassWordChangeDto {
    private String oldPwd;
    private String newPwd;
    private String userId;
}
