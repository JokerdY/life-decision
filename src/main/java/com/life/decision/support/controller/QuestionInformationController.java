package com.life.decision.support.controller;

import cn.hutool.core.util.StrUtil;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionInformation;
import com.life.decision.support.service.IQuestionInformationService;
import com.life.decision.support.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("list")
    @ResponseBody
    public Object questionInformationList(@RequestBody QuestionInformation questionInformation) {
        try {
            return ResultUtils.returnSuccess(questionInformationService.listById(questionInformation.getQuestionnaireId()));
        } catch (Exception e) {
            log.error("问卷明细查询异常", e);
            return ResultUtils.returnError("问卷明细查询异常，请联系管理员：" + e.getMessage());
        }
    }

    /**
     * 修改问卷
     * @param questionAnswer
     * @return
     */
    @RequestMapping("listWithEdit")
    @ResponseBody
    public Object listWithEdit(@RequestBody QuestionAnswer questionAnswer) {
        if (StrUtil.isBlank(questionAnswer.getQuestionnaireId()) || StrUtil.isBlank(questionAnswer.getUserId())) {
            return ResultUtils.returnError("问卷id或用户id为空");
        }
        return ResultUtils.returnSuccess(questionInformationService.findEditList(questionAnswer));
    }
}
