package com.life.decision.support.mapper;

import com.life.decision.support.dto.SubmitOfTheQuestionnaireGroup;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionnaireSubmitInformationMapper {
    List<QuestionnaireSubmitInformation> findSubmitPage();

    void insert(QuestionnaireSubmitInformation submitInfo);

    void update(QuestionnaireSubmitInformation submitInfo);

    QuestionnaireSubmitInformation getById(@Param("id") String id);

    List<SubmitOfTheQuestionnaireGroup> listSubmitMsg(@Param("userId") String userId);
}
