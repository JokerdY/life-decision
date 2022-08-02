package com.life.decision.support.service;

import cn.hutool.json.JSONObject;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;

import java.time.LocalDateTime;
import java.util.List;

public interface IQuestionnaireGroupInformationService {
    List<QuestionnaireGroupInformation> findList(QuestionnaireGroupInformation entity);

    List<JSONObject> findGroupList(String userId);

    String save(String userId, String submitId, String questionnaireId, LocalDateTime createDate);

    void updateBySubmit(String submitId);

    QuestionnaireGroupInformation getBySubmitId(String submitId);

    List<JSONObject> findGroupSubmitList(String userId);

    QuestionnaireGroupInformation getByUserIdHasSuccess(String userId);
}
