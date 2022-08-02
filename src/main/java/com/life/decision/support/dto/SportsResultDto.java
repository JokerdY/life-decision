package com.life.decision.support.dto;

import com.life.decision.support.pojo.SportsResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SportsResultDto extends SportsResult {
    private String queryDate;
}
