package com.life.decision.support.vo;

import com.life.decision.support.pojo.UserInformation;
import lombok.Data;

@Data
public class ModuleAccessCountVo {
    private String id;
    private String type;
    private String typeName;
    private UserInformation user;
    private String api;
    private Integer count;
}
