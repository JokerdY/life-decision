package com.life.decision.support.controller;

import com.life.decision.support.mapper.QuestionnaireSubmitInformationMapper;
import com.life.decision.support.utils.PageUtils;
import com.life.decision.support.utils.ResultUtils;
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
        return ResultUtils.returnPage(PageUtils.getPageResult(page, size,
                questionnaireSubmitInformationMapper.findSubmitPage()));
    }

}
