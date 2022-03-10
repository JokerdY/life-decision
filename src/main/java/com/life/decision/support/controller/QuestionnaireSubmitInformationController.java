package com.life.decision.support.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.life.decision.support.mapper.QuestionnaireSubmitInformationMapper;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.service.IQuestionnaireInformationService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/questionnaireSubmitInformation")
public class QuestionnaireSubmitInformationController {

    @Autowired
    QuestionnaireSubmitInformationMapper questionnaireSubmitInformationMapper;

    @RequestMapping("questionnaireList")
    @ResponseBody
    public Object questionnaireList(@RequestParam Integer page, @RequestParam Integer size) {
        PageHelper.startPage(page,size);
        Page<QuestionnaireSubmitInformation> submitPage = questionnaireSubmitInformationMapper.findSubmitPage();
        return submitPage;
    }
}
