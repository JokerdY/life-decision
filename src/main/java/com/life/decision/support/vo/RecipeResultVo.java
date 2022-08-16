package com.life.decision.support.vo;

import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.bo.RecipeEntity;
import com.life.decision.support.bo.TotalCaloriesEntity;
import lombok.Data;

import java.util.List;

@Data
public class RecipeResultVo {
    private List<ContentAdvice> healthEducation;

    private List<String> dateRecord;

    private RecipeEntity breakfastRecipe;

    private RecipeEntity lunchRecipe;

    private RecipeEntity dinnerRecipe;

    private TotalCaloriesEntity totalCaloriesEntity;

    private List<String> dietaryAdvice;

    private String nowDay;
}
