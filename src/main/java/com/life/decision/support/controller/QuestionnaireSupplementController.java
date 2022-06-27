package com.life.decision.support.controller;

import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.dto.SysDictDto;
import com.life.decision.support.dto.UserAndQuestionnaireDto;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.IQuestionInformationService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.service.ISysDictService;
import com.life.decision.support.service.IUserInformationService;
import com.life.decision.support.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据补录
 */
@RestController
@RequestMapping("/questionnaireSupplement")
public class QuestionnaireSupplementController {

    @Autowired
    IUserInformationService userInformationService;
    @Autowired
    IQuestionInformationService questionInformationService;
    @Autowired
    IQuestionnaireSubmitInformationService questionnaireSubmitInformationService;
    @Autowired
    ISysDictService dictService;

    /**
     * 数据补录提供用户+问卷类型联动接口
     */
    @RequestMapping("userAndQuestionnaireId")
    public Object userAndQuestionnaireId() {
        List<SysDictDto> questionnaire = dictService.dictList("questionnaire_type");
        // 根据用户id分组得到所有用户的提交问卷组信息
        Map<String, List<JSONObject>> groupByUserId = questionnaireSubmitInformationService.listGroupByUserIdAndGroupId()
                .stream()
                .collect(Collectors.groupingBy(s -> s.getStr("userId")));
        // 获取用户信息
        List<UserInformationDto> list = userInformationService.findAllList(new UserInformationDto());
        List<UserAndQuestionnaireDto> result = new ArrayList<>();
        for (UserInformationDto user : list) {
            UserAndQuestionnaireDto temp = new UserAndQuestionnaireDto();
            temp.setUser(user);
            Optional<JSONObject> maxOption = groupByUserId.getOrDefault(user.getId(), new ArrayList<>()).stream()
                    .min(Comparator.comparingInt(s -> s.getInt("count")));
            if (maxOption.isPresent()) {
                JSONObject maxSubmitGroup = maxOption.get();
                if (maxSubmitGroup.getInt("count") == 5) {
                    maxSubmitGroup.putOpt("questionnaireId", "");
                }
                List<SysDictDto> questionnaireId = questionnaire.stream().filter(s -> !maxSubmitGroup.getStr("questionnaireId").contains(s.getId()))
                        .collect(Collectors.toList());
                temp.setList(questionnaireId);
            } else {
                temp.setList(questionnaire);
            }
            result.add(temp);
        }
        return ResultUtils.returnSuccess(result);
    }

    /**
     * 数据补录新增接口
     *
     * @param map
     * @return
     */
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
        if (userMsg == null) {
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
