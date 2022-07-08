package com.life.decision.support.mapper;

import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.SubmitOfTheQuestionnaireGroup;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionnaireSubmitInformationMapper {
    List<QuestionnaireSubmitInformation> findSubmitPage(QuestionnaireSubmitInformation dto);

    void insert(QuestionnaireSubmitInformation submitInfo);

    void update(QuestionnaireSubmitInformation submitInfo);

    QuestionnaireSubmitInformation getById(@Param("id") String id);

    List<SubmitOfTheQuestionnaireGroup> listSubmitMsg(@Param("userId") String userId);

    List<QuestionnaireSubmitInformation> listIdByGroupIds(@Param("list") List<String> groupIds);

    List<String> getGroupIdByDate(@Param("startDate") String startDate,
                                  @Param("endDate") String endDate);

    List<JSONObject> listGroupByUserIdAndGroupId();

    List<QuestionnaireSubmitInformation> listLatestSubmittedQuestionnaire(@Param("userId") String userId);

    /**
     * 查看已完成的数量
     */
    Integer getCountByHasFinish(@Param("ids") List<String> ids);

    List<QuestionnaireSubmitInformation> listByGroupId(@Param("groupId") String groupId);
}
