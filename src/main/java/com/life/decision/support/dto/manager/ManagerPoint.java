package com.life.decision.support.dto.manager;

import com.life.decision.support.bo.UrlAdvice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagerPoint extends UrlAdvice {
    /**
     * 头颈部穴位按摩 上肢穴位按摩 下肢穴位按摩 腹部穴位按摩 耳部穴位按摩
     */
    private String type;
}
