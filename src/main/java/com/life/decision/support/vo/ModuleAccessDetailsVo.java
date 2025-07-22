package com.life.decision.support.vo;

import com.life.decision.support.pojo.UserInformation;
import lombok.Data;

import java.util.Date;

@Data
public class ModuleAccessDetailsVo {
    private String id;
    private String type;
    private String typeName;
    private UserInformation user;
    private Date date;
}
