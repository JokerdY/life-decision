package com.life.decision.support.vo;

import com.life.decision.support.bo.MassageVo;
import com.life.decision.support.bo.UrlAdvice;
import lombok.Data;

import java.util.List;

@Data
public class ChineseMedicineVo {
    private String id;
    private List<UrlAdvice> musicList;
    private List<MassageVo> MassageList;
}
