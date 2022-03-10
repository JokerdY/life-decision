package com.life.decision.support.mapper;

import com.github.pagehelper.Page;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionnaireSubmitInformationMapper  {
    Page<QuestionnaireSubmitInformation> findSubmitPage();
}
