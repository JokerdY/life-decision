package com.life.decision.support.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.life.decision.support.mapper.QuestionnaireGroupInformationMapper;
import com.life.decision.support.mapper.QuestionnaireSubmitInformationMapper;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import com.life.decision.support.service.IQuestionnaireGroupInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionnaireGroupInformationServiceImpl implements IQuestionnaireGroupInformationService {

    @Autowired
    QuestionnaireGroupInformationMapper mapper;
    @Autowired
    QuestionnaireSubmitInformationMapper submitInformationMapper;

    @Override
    public List<QuestionnaireGroupInformation> findList(QuestionnaireGroupInformation entity) {
        return mapper.findList(entity);
    }

    public List<JSONObject> findGroupSubmitList(String userId) {
        List<JSONObject> groupSubmitList = mapper.findGroupList(userId);
        groupSubmitList.forEach(json -> {
            Integer sum = submitInformationMapper.getCountByHasFinish(
                    Arrays.asList(json.getStr("groupSubmitId").split(",")));
            json.putOpt("sum", sum);
            if (sum == 5) {
                json.putOnce("finished", true);
            } else {
                json.putOnce("finished", false);
            }
        });
        return groupSubmitList;
    }

    @Override
    public List<JSONObject> findGroupList(String userId) {
        return mapper.findGroupList(userId);
    }

    @Override
    public String save(String userId, String submitId, String questionnaireId, LocalDateTime createDate) {
        QuestionnaireGroupInformation entity = new QuestionnaireGroupInformation(questionnaireId, submitId, userId, createDate, createDate);
        List<JSONObject> groupList = mapper.findGroupList(userId);
        String groupId = IdUtil.fastUUID();
        for (JSONObject json : groupList) {
            if (!json.getStr("groupQuestionnaireId").contains(questionnaireId)) {
                groupId = json.getStr("groupId");
                break;
            }
        }
        entity.setGroupId(groupId);
        mapper.insert(entity);
        return groupId;
    }

    @Override
    public void updateBySubmit(String submitId) {
        mapper.updateDateBySubmitId(submitId);
    }

    @Override
    public QuestionnaireGroupInformation getBySubmitId(String submitId) {
        return mapper.getBySubmitId(submitId);
    }

}
