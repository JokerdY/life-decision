package com.life.decision.support.service.impl;

import com.life.decision.support.dto.SubmitOfTheQuestionnaireGroup;
import com.life.decision.support.mapper.QuestionnaireSubmitInformationMapper;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public QuestionnaireSubmitInformation getById(String submitId) {
        return mapper.getById(submitId);
    }

    @Override
    public List<SubmitOfTheQuestionnaireGroup> listSubmitMsg(String userId) {
        return mapper.listSubmitMsg(userId);
    }

    @Override
    public List<QuestionnaireSubmitInformation> findSubmitPage(QuestionnaireSubmitInformation dto) {
        return mapper.findSubmitPage(dto);
    }
}
