package com.life.decision.support.vo;

import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.bo.UrlAdvice;
import lombok.Data;

import java.util.List;

@Data
public class PsychologyResultVo {
    private String evaluationTitle;
    private String evaluationResults;
    private List<ContentAdvice> psychologicalAdvices;
    private List<UrlAdvice> healthAdvices;
    private String healthContent;
}
