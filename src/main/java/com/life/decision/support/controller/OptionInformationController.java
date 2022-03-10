package com.life.decision.support.controller;

import com.life.decision.support.service.IQuestionnaireInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/optionInformation")
public class OptionInformationController {

    @Autowired
    IQuestionnaireInformationService questionnaireInformationService;


    /**
     * demo
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public String insert() {
        return "!";
    }

}
