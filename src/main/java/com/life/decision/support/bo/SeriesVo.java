package com.life.decision.support.bo;

import lombok.Data;

import java.util.List;

@Data
public class SeriesVo {
    private String name;
    private List<String> data;

    public SeriesVo(String name, List<String> data) {
        this.name = name;
        this.data = data;
    }

}
