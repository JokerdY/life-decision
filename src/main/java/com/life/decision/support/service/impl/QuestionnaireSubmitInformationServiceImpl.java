package com.life.decision.support.service.impl;

import cn.hutool.json.JSONObject;
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
    public List<QuestionnaireSubmitInformation> listByGroupId(String groupId) {
        return mapper.listByGroupId(groupId);
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

    public List<QuestionnaireSubmitInformation> listIdByGroupIds(List<String> groupIds) {
        return mapper.listIdByGroupIds(groupIds);
    }

    @Override
    public List<String> getGroupIdsByDate(String startDate, String endDate) {
        return mapper.getGroupIdByDate(startDate, endDate);
    }

    @Override
    public List<QuestionnaireSubmitInformation> listLatestSubmittedQuestionnaire(String userId) {
        return mapper.listLatestSubmittedQuestionnaire(userId);
    }

    @Override
    public List<JSONObject> listGroupByUserIdAndGroupId() {
        return mapper.listGroupByUserIdAndGroupId();
    }
}
