package com.life.decision.support.dto.manager;

import com.life.decision.support.bo.UrlAdvice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagerSportChild extends UrlAdvice {
    /**
     * 运动前热身 具体的运动 运动后拉伸
     */
    private String type;
}
