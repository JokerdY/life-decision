package com.life.decision.support.service;


import com.life.decision.support.pojo.QuestionnaireSubmitInformation;

public interface IQuestionnaireSubmitInformationService{

    void save(QuestionnaireSubmitInformation submitInfo);

    void update(QuestionnaireSubmitInformation submitInfo);
}
