package com.life.decision.support.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @RequestMapping("save")
    @ResponseBody
    @Transactional
    public Object saveBatch(@RequestBody JSONObject obj) {
        List<QuestionAnswer> list = obj.getJSONArray("list")
                .toList(QuestionAnswer.class);
        String userId = obj.getStr("userId");
        String questionnaireId = obj.getStr("questionnaireId");
        // 插入内容
        LocalDateTime now = LocalDateTime.now();
        String id = IdUtil.fastUUID();
        questionAnswerService.saveBatch(list, userId, questionnaireId, now, id);
        QuestionnaireSubmitInformation submitInfo = new QuestionnaireSubmitInformation();
        submitInfo.setQuestionnaireId(questionnaireId);
        submitInfo.setUserId(userId);
        submitInfo.setCreateTime(now);
        submitInfo.setUpdateTime(now);
        submitInfo.setId(id);
        questionnaireSubmitInformationService.save(submitInfo);
        return ResultUtils.returnSuccess();
    }

    @RequestMapping("update")
    @ResponseBody
    @Transactional
    public Object update(@RequestBody JSONObject obj) {
        List<QuestionAnswer> list = obj.getJSONArray("list").toList(QuestionAnswer.class);
        if (CollUtil.isNotEmpty(list)) {
            questionAnswerService.updateBatch(list);
            QuestionnaireSubmitInformation submitInfo = new QuestionnaireSubmitInformation();
            submitInfo.setId(list.get(0).getSubmitId());
            questionnaireSubmitInformationService.update(submitInfo);
        }
        return ResultUtils.returnSuccess();
    }

}
