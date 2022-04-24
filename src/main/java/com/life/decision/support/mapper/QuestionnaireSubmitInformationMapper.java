package com.life.decision.support.mapper;

import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionnaireSubmitInformationMapper  {
    List<QuestionnaireSubmitInformation> findSubmitPage();

    void insert(QuestionnaireSubmitInformation submitInfo);

    void update(QuestionnaireSubmitInformation submitInfo);
}
