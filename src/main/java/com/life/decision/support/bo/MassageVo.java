package com.life.decision.support.bo;

import lombok.Data;

import java.util.List;

@Data
public class MassageVo {
    /**
     * 穴位list
     */
    private List<UrlAdvice> acupuncturePointsList;
    private String name;
}
