package com.life.decision.support.service;


import com.life.decision.support.dto.SubmitOfTheQuestionnaireGroup;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;

import java.util.List;

public interface IQuestionnaireSubmitInformationService {

    void save(QuestionnaireSubmitInformation submitInfo);

    void update(QuestionnaireSubmitInformation submitInfo);

    QuestionnaireSubmitInformation getById(String submitId);

    List<SubmitOfTheQuestionnaireGroup> listSubmitMsg(String userId);

    List<QuestionnaireSubmitInformation> findSubmitPage(QuestionnaireSubmitInformation dto);
}
