package com.life.decision.support.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/questionnaireSubmitInformation")
public class QuestionnaireSubmitInformationController {

    @Autowired
    IQuestionnaireSubmitInformationService questionnaireSubmitInformationService;

    /**
     * orderby排序传数据库字段
     *
     * @param dto
     * @return 参数   用户账号 user.telphoneNum
     * 用户名   user.userName
     * 测评类型 questionnaireId
     * 填写时间 createTime
     */
    @PostMapping("adminQuestionnaireList")
    @ResponseBody
    public Object adminQuestionnaireList(@RequestBody QuestionnaireSubmitInformation dto) {
        PageHelper.startPage(dto);
        List<QuestionnaireSubmitInformation> list = questionnaireSubmitInformationService.findSubmitPage(dto);
        return ResultUtils.returnPage(new PageInfo<>(list));
    }

    @PostMapping("adminQuestionnaireListHasSubmit")
    @ResponseBody
    public Object adminQuestionnaireListHasSubmit(@RequestBody QuestionnaireSubmitInformation dto) {
        dto.setQuestionnaireStatus(1);
        PageHelper.startPage(dto);
        List<QuestionnaireSubmitInformation> list = questionnaireSubmitInformationService.findSubmitPage(dto);
        return ResultUtils.returnPage(new PageInfo<>(list));
    }

}
