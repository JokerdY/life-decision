package com.life.decision.support.controller;

import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.IQuestionInformationService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.service.IUserInformationService;
import com.life.decision.support.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/questionnaireSupplement")
public class QuestionnaireSupplementController {

    @Autowired
    IUserInformationService userInformationService;
    @Autowired
    IQuestionInformationService questionInformationService;
    @Autowired
    IQuestionnaireSubmitInformationService questionnaireSubmitInformationService;

    @RequestMapping("supplementaryRecordByUserId")
    public Object supplementaryRecordByUserId(@RequestBody Map<String, String> map) {
        if (!map.containsKey("questionnaireId") || !map.containsKey("userTel")) {
            return ResultUtils.returnError("参数不完整！");
        }
        String questionnaireId = map.get("questionnaireId");
        String userTel = map.get("userTel");
        // 返回用户信息和问卷信息
        UserInformation userInformation = new UserInformation();
        userInformation.setTelphoneNum(userTel);
        return getSupplementaryResult(questionInformationService.listById(questionnaireId), userInformation);
    }

    private Map<String, Object> getSupplementaryResult(List<QuestionInformationDto> questionInformationDtoList, UserInformation userInformation) {
        JSONObject result = new JSONObject();
        UserInformationDto userMsg = userInformationService.getUserMsg(userInformation);
        if(userMsg==null){
            return ResultUtils.returnError("用户账号不存在!");
        }
        result.putOpt("userMsg", userMsg);
        result.putOpt("questionInformation", questionInformationDtoList);
        return ResultUtils.returnSuccess(result);
    }

    /**
     * 编辑
     *
     * @param map
     * @return
     */
    @RequestMapping("supplementaryEditByUserId")
    public Object supplementaryEditByUserId(@RequestBody Map<String, String> map) {
        // 编辑 传submitId
        if (!map.containsKey("submitId")) {
            return ResultUtils.returnError("参数不完整！");
        }
        String submitId = map.get("submitId");
        QuestionnaireSubmitInformation submitInfo = questionnaireSubmitInformationService.getById(submitId);
        // 返回用户信息和问卷信息
        UserInformation userInformation = new UserInformation();
        userInformation.setId(submitInfo.getUserId());
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setSubmitId(submitId);
        return getSupplementaryResult(questionInformationService.findEditList(questionAnswer), userInformation);
    }

}
