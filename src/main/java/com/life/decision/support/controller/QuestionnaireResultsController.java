package com.life.decision.support.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.mapper.QuestionInformationMapper;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.result.QuestionInformationResultDto;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionnaireGroupInformationService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/result")
public class QuestionnaireResultsController {
    @Autowired
    IQuestionnaireGroupInformationService groupInformationService;
    @Autowired
    IQuestionnaireSubmitInformationService submitInformationService;
    @Autowired
    private QuestionInformationMapper informationMapper;
    @Autowired
    private IQuestionAnswerService questionAnswerService;

    @RequestMapping("getResult")
    public JSONObject getResult(@RequestBody UserInformationDto user) {
        String id = StrUtil.isBlank(user.getId()) ? user.getUserId() : user.getId();
        List<QuestionnaireSubmitInformation> submitList = submitInformationService.listLatestSubmittedQuestionnaire(id);
        return getQuestionnaireGroupAnswer(id, submitList);
    }

    private JSONObject getQuestionnaireGroupAnswer(String userId, List<QuestionnaireSubmitInformation> submitList) {
        JSONObject result = new JSONObject();
        result.putOpt("userId", userId);
        JSONObject questionnaire = new JSONObject();
        for (QuestionnaireSubmitInformation info : submitList) {
            List<QuestionInformationDto> list = informationMapper.listById(info.getQuestionnaireId());
            QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.setUserId(userId);
            questionAnswer.setQuestionnaireId(info.getQuestionnaireId());
            questionAnswer.setSubmitId(info.getId());
            List<QuestionAnswer> answerList = questionAnswerService.findList(questionAnswer);
            JSONArray question = new JSONArray();
            for (QuestionInformationDto questionInformationDto : list) {
                uploadAnswer(answerList, questionInformationDto);
                question.add(BeanUtil.copyProperties(questionInformationDto, QuestionInformationResultDto.class));
                if (CollUtil.isNotEmpty(questionInformationDto.getChild())) {
                    for (QuestionInformationDto informationDto : questionInformationDto.getChild()) {
                        uploadAnswer(answerList, informationDto);
                        question.add(BeanUtil.copyProperties(informationDto, QuestionInformationResultDto.class));
                    }
                }
            }
            questionnaire.putOpt(info.getQuestionnaireId(), question);
        }
        result.putOpt("questionnaire", questionnaire);
        return result;
    }

    @RequestMapping("getResultByGroupId")
    public JSONObject getResultByGroupId(@RequestBody QuestionnaireGroupInformation groupInformation) {
        List<QuestionnaireSubmitInformation> submitList = submitInformationService.listByGroupId(groupInformation.getGroupId());
        JSONObject result = getQuestionnaireGroupAnswer(groupInformation.getUserId(), submitList);
        return result;
    }

    private void uploadAnswer(List<QuestionAnswer> answerList, QuestionInformationDto dto) {
        answerList.stream()
                .filter(s -> s.getQuestionId().equals(dto.getId()))
                .findAny()
                .ifPresent(dto::setQuestionAnswer);
    }
}
