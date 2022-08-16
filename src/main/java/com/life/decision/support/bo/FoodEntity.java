package com.life.decision.support.bo;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class FoodEntity {
    private final String foodName;
    private final String weight;
    private final String calories;
    private final Double caloriesDouble;
    private final String category;

    public FoodEntity(String foodName, String weight, String calories, String category) {
        this.foodName = foodName;
        double d, w;
        try {
            d = Double.parseDouble(calories);
        } catch (Throwable e) {
            d = 0.0;
        }
        try {
            w = Double.parseDouble(weight);
        } catch (Throwable e) {
            w = 0.0;
        }
        this.caloriesDouble = d;
        this.calories = new DecimalFormat("0").format(d);
        this.weight = new DecimalFormat("0").format(w);
        this.category = category;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getWeight() {
        return weight;
    }

    public String getCalories() {
        return calories;
    }

    public Double getCaloriesDouble() {
        return caloriesDouble;
    }
}
