package com.life.decision.support.dto.manager;

import lombok.Data;

@Data
public class ManagerElement {
    private String tanshuiProportion;
    private String tanshuiWeight;
    private String danbaizhiProportion;
    private String danbaizhiWeight;
    private String zhifangProportion;
    private String zhifangWeight;

    public ManagerElement(String tanshuiProportion, String tanshuiWeight, String danbaizhiProportion, String danbaizhiWeight, String zhifangProportion, String zhifangWeight) {
        this.tanshuiProportion = tanshuiProportion;
        this.tanshuiWeight = tanshuiWeight;
        this.danbaizhiProportion = danbaizhiProportion;
        this.danbaizhiWeight = danbaizhiWeight;
        this.zhifangProportion = zhifangProportion;
        this.zhifangWeight = zhifangWeight;
    }

    public ManagerElement() {
    }
}
