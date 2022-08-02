package com.life.decision.support.dto;

import com.life.decision.support.pojo.RecipeResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecipeResultDto extends RecipeResult {
    private String queryDate;
}
