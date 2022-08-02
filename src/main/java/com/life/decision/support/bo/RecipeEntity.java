package com.life.decision.support.bo;

import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeEntity {
    private List<FoodEntity> foodEntities;
    private String totalCalories;

    public static class Builder {
        private RecipeEntity entity;

        public Builder builder() {
            entity = new RecipeEntity();
            return this;
        }

        public Builder food(FoodEntity foodEntity) {
            if (entity.getFoodEntities() == null) {
                entity.foodEntities = new ArrayList<>();
            }
            entity.foodEntities.add(foodEntity);
            return this;
        }

        public RecipeEntity build() {
            double totalCal = 0.0;
            if (entity.getFoodEntities() != null) {
                for (FoodEntity foodEntity : entity.getFoodEntities()) {
                    totalCal += foodEntity.getCaloriesDouble();
                }
            }
            entity.totalCalories = new DecimalFormat("0").format(totalCal);
            return entity;
        }
    }

    private RecipeEntity() {
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public List<FoodEntity> getFoodEntities() {
        return foodEntities;
    }

    public void setFoodEntities(List<FoodEntity> foodEntities) {
        this.foodEntities = foodEntities;
    }

}
