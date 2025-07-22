package com.life.decision.support.bo;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class FoodEntity {
    private String foodName;
    private String weight;
    private String calories;
    private Double caloriesDouble;
    private String category;

    public FoodEntity() {
    }

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

}
