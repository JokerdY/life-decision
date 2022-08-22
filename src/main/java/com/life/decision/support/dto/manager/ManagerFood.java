package com.life.decision.support.dto.manager;

import com.life.decision.support.bo.FoodEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ManagerFood extends FoodEntity {
    /**
     * 早餐 中餐 晚餐
     */
    private String canci;

    public ManagerFood() {
    }
}
