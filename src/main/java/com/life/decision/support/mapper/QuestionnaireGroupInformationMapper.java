package com.life.decision.support.mapper;

import cn.hutool.json.JSONObject;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionnaireGroupInformationMapper {

    List<QuestionnaireGroupInformation> findList(QuestionnaireGroupInformation entity);

    List<JSONObject> findGroupList(@Param("userId") String userId);

    void insert(QuestionnaireGroupInformation entity);

    void updateDateBySubmitId(@Param("submitId") String submitId);

    QuestionnaireGroupInformation getBySubmitId(@Param("submitId") String submitId);

    /**
     * 找到未完成的问卷组
     *
     * @param userId
     * @return
     */
    QuestionnaireGroupInformation getByUserId(@Param("userId") String userId);

    /**
     * 找到未完成的问卷组
     *
     * @param userId
     * @return
     */
    QuestionnaireGroupInformation getByUserIdHasSuccess(@Param("userId") String userId);

}
