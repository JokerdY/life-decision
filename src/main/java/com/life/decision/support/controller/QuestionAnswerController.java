package com.life.decision.support.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.QuestionnaireInformationUserDto;
import com.life.decision.support.dto.SubmitOfTheQuestionnaireGroup;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Controller
@RequestMapping("/questionAnswer")
public class QuestionAnswerController {

    @Autowired
    private IQuestionAnswerService questionAnswerService;
    @Autowired
    private IQuestionnaireSubmitInformationService questionnaireSubmitInformationService;

    @PostMapping("save")
    @ResponseBody
    @Transactional
    public Object saveBatch(@RequestBody JSONObject obj) {
        List<QuestionAnswer> list = obj.getJSONArray("list")
                .toList(QuestionAnswer.class);
        String userId = obj.getStr("userId");
        String questionnaireId = obj.getStr("questionnaireId");
        // 插入内容
        LocalDateTime now = LocalDateTime.now();
        String submitId = IdUtil.fastUUID();
        questionAnswerService.saveBatch(list, userId, questionnaireId, now, submitId);
        QuestionnaireSubmitInformation submitInfo = new QuestionnaireSubmitInformation();
        submitInfo.setQuestionnaireId(questionnaireId);
        submitInfo.setUserId(userId);
        submitInfo.setCreateTime(now);
        submitInfo.setUpdateTime(now);
        submitInfo.setId(submitId);
        questionnaireSubmitInformationService.save(submitInfo);
        // 提交修改结束后 都要返回 当前问卷组内 未完成的问卷
        return ResultUtils.returnSuccess(getQuestionnaireGroup(userId));
    }

    private List<SubmitOfTheQuestionnaireGroup> getQuestionnaireGroup(String userId) {
        QuestionnaireInformationUserDto dto = new QuestionnaireInformationUserDto();
        dto.setUserId(userId);
        List<SubmitOfTheQuestionnaireGroup> result = questionnaireSubmitInformationService.listSubmitMsg(userId);
        result.stream()
                .map(SubmitOfTheQuestionnaireGroup::getSubmitCount)
                .max(Integer::compareTo)
                .ifPresent(max -> {
                    for (SubmitOfTheQuestionnaireGroup info : result) {
                        if (max.equals(info.getSubmitCount())) {
                            info.setIsSubmit(true);
                        } else {
                            info.setIsSubmit(false);
                        }
                    }
                });
        return result;
    }

    @PostMapping("update")
    @ResponseBody
    @Transactional
    public Object update(@RequestBody JSONObject obj) {
        List<QuestionAnswer> list = obj.getJSONArray("list").toList(QuestionAnswer.class);
        if (CollUtil.isNotEmpty(list)) {
            questionAnswerService.updateBatch(list);
            QuestionnaireSubmitInformation submitInfo = new QuestionnaireSubmitInformation();
            submitInfo.setId(list.get(0).getSubmitId());
            questionnaireSubmitInformationService.update(submitInfo);
            return ResultUtils.returnSuccess(getQuestionnaireGroup(list.get(0).getUserId()));
        }
        return ResultUtils.returnSuccess();
    }

}
