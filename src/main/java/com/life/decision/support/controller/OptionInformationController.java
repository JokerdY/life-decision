package com.life.decision.support.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.life.decision.support.pojo.QuestionInformation;
import com.life.decision.support.pojo.QuestionnaireInformation;
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
        QuestionnaireInformation s = QuestionnaireInformation.builder()
                .questionnaireType(1)
                .questionnaireDescription("问卷1")
                .build();
        QuestionnaireInformation s1 = QuestionnaireInformation.builder()
                .questionnaireType(2)
                .questionnaireDescription("问卷2")
                .build();
        questionnaireInformationService.save(s);
        questionnaireInformationService.save(s1);
        UpdateWrapper<QuestionnaireInformation> wrapper = new UpdateWrapper<>();
        wrapper.eq("questionnaire_type", 1).set("questionnaire_description", "问卷3");
        questionnaireInformationService.update(wrapper);
        return "!";
    }

}
