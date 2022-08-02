package com.life.decision.support.vo;

import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.bo.UrlAdvice;
import lombok.Data;

import java.util.List;

@Data
public class SportResultVo{
    private List<UrlAdvice> beforeSports;

    private List<UrlAdvice> specialSports;

    private List<UrlAdvice> afterSports;

    private List<ContentAdvice> healthEducation;

    private List<String> dateRecord;

}
