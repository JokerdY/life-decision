package com.life.decision.support.dto;

import com.life.decision.support.common.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModuleAccessDetailsDto extends PageDto {
    private String id;
    private String type;
    private String userName;
    private Date queryStartDate;
    private Date queryEndDate;
    private String api;
}
