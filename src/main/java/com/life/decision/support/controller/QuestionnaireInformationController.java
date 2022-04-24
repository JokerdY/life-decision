package com.life.decision.support.controller;

import com.github.pagehelper.PageInfo;
import com.life.decision.support.dto.QuestionnaireInformationUserDto;
import com.life.decision.support.pojo.QuestionnaireInformation;
import com.life.decision.support.service.IQuestionnaireInformationService;
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
@RequestMapping("/questionnaireInformation")
public class QuestionnaireInformationController {

    @Autowired
    IQuestionnaireInformationService questionnaireInformationService;

    @RequestMapping("adminPage")
    @ResponseBody
    public Object questionnaireInfoPage(@RequestBody QuestionnaireInformation questionnaireInformation) {
        try {
            return ResultUtils.returnPage(new PageInfo<>(questionnaireInformationService.findList(questionnaireInformation)));
        } catch (Exception e) {
            log.error("问卷概要获取失败", e);
            return ResultUtils.returnError("信息获取错误，请联系管理员:" + e.getMessage());
        }
    }

    @RequestMapping("page")
    @ResponseBody
    public Object page(@RequestBody QuestionnaireInformationUserDto dto) {
        try {
            return ResultUtils.returnPage(new PageInfo<>(questionnaireInformationService.findListInUser(dto)));
        } catch (Exception e) {
            log.error("问卷概要信息获取失败", e);
            return ResultUtils.returnError("问卷概要信息获取失败，请联系管理员：" + e.getMessage());
        }
    }
}
