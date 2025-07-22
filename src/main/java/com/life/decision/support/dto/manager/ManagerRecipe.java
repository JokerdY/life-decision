package com.life.decision.support.dto.manager;

import com.life.decision.support.vo.RecipeResultVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagerRecipe extends RecipeResultVo {
    private List<ManagerFood> managerFoods;

    private ManagerElement managerElement;
}
