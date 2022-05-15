package com.life.decision.support.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionInformation;
import com.life.decision.support.pojo.QuestionnaireInformation;
import com.life.decision.support.service.IQuestionInformationService;
import com.life.decision.support.service.IQuestionnaireInformationService;
import com.life.decision.support.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Slf4j
@Controller
@RequestMapping("/questionInformation")
public class QuestionInformationController {

    @Autowired
    IQuestionInformationService questionInformationService;
    @Autowired
    IQuestionnaireInformationService questionnaireInformationService;

    @PostMapping("list")
    @ResponseBody
    public Object questionInformationList(@RequestBody QuestionInformation questionInformation) {
        try {
            List<QuestionInformationDto> data = questionInformationService.listById(questionInformation.getQuestionnaireId());
            return ResultUtils.returnSuccess(getContent(questionInformation.getQuestionnaireId(), data));
        } catch (Exception e) {
            log.error("问卷明细查询异常", e);
            return ResultUtils.returnError("问卷明细查询异常，请联系管理员：" + e.getMessage());
        }
    }

    private JSONObject getContent(String questionnaireId, List<QuestionInformationDto> data) {
        QuestionnaireInformation questionnaireInformation = questionnaireInformationService.selectByPrimaryKey(questionnaireId);
        JSONObject result = new JSONObject();
        result.putOnce("data", data);
        result.putOnce("content", questionnaireInformation.getQuestionnaireDescription());
        return result;
    }

    /**
     * 修改问卷
     *
     * @param questionAnswer
     * @return
     */
    @PostMapping("listWithEdit")
    @ResponseBody
    public Object listWithEdit(@RequestBody QuestionAnswer questionAnswer) {
        if (StrUtil.isBlank(questionAnswer.getSubmitId()) || StrUtil.isBlank(questionAnswer.getUserId())) {
            return ResultUtils.returnError("问卷id或用户id为空");
        }
        List<QuestionInformationDto> editList = questionInformationService.findEditList(questionAnswer);
        return ResultUtils.returnSuccess(getContent(questionAnswer.getQuestionnaireId(), editList));
    }
}
