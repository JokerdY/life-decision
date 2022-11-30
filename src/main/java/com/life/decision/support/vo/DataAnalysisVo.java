package com.life.decision.support.vo;

import com.life.decision.support.bo.SeriesVo;
import lombok.Data;

import java.util.List;

/**
 * 折线图
 */
@Data
public class DataAnalysisVo {
    private List<String> xAxis;
    private List<SeriesVo> dataList;
    private List<String> yAxis;
}
