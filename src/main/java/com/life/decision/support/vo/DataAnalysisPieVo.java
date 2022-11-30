package com.life.decision.support.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 饼图
 */
public class DataAnalysisPieVo {

    private List<PieData> data;

    public List<PieData> getData() {
        return data;
    }

    public void setData(List<PieData> data) {
        this.data = data;
    }

    @Data
    static class PieData {
        private String value;
        private String name;
    }

    public static DataAnalysisPieVo build(Map<String, ?> map) {
        DataAnalysisPieVo vo = new DataAnalysisPieVo();
        List<PieData> list = new ArrayList<>();
        map.forEach((k, v) -> {
            PieData pieData = new PieData();
            pieData.setName(k);
            if (v instanceof List) {
                pieData.setValue(((List<?>) v).size() + "");
            } else {
                pieData.setValue(v + "");
            }
            list.add(pieData);
        });
        vo.setData(list);
        return vo;
    }


}
