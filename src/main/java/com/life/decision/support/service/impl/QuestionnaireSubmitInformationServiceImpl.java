package com.life.decision.support.service.impl;

import com.life.decision.support.mapper.QuestionnaireSubmitInformationMapper;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireSubmitInformationServiceImpl implements IQuestionnaireSubmitInformationService {

    @Autowired
    QuestionnaireSubmitInformationMapper mapper;

    @Override
    public void save(QuestionnaireSubmitInformation submitInfo) {
        mapper.insert(submitInfo);
    }

    @Override
    public void update(QuestionnaireSubmitInformation submitInfo) {
        mapper.update(submitInfo);
    }
}
