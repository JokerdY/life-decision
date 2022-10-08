package com.life.decision.support.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class ModuleAccessDetails {
    private String id;
    private String type;
    private String userId;
    private Date createDate;
    private String api;
}
