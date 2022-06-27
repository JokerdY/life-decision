package com.life.decision.support.service;


import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.SubmitOfTheQuestionnaireGroup;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;

import java.util.List;

public interface IQuestionnaireSubmitInformationService {

    void save(QuestionnaireSubmitInformation submitInfo);

    void update(QuestionnaireSubmitInformation submitInfo);

    List<QuestionnaireSubmitInformation> listByGroupId(String groupId);

    QuestionnaireSubmitInformation getById(String submitId);

    List<SubmitOfTheQuestionnaireGroup> listSubmitMsg(String userId);

    List<QuestionnaireSubmitInformation> findSubmitPage(QuestionnaireSubmitInformation dto);

    List<QuestionnaireSubmitInformation> listIdByGroupIds(List<String> groupIds);

    List<String> getGroupIdsByDate(String startDate, String endDate);

    List<JSONObject> listGroupByUserIdAndGroupId();

    List<QuestionnaireSubmitInformation> listLatestSubmittedQuestionnaire(String userId);
}
