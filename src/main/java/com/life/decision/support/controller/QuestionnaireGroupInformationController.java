package com.life.decision.support.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.life.decision.support.service.IQuestionnaireGroupInformationService;
import com.life.decision.support.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/questionGroupInformation")
public class QuestionnaireGroupInformationController {

    @Autowired
    IQuestionnaireGroupInformationService groupInformationService;

    @PostMapping("findGroupSubmitList")
    public Object findGroupSubmitList(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getStr("userId");
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("参数缺失，请传入userId");
        }
        return ResultUtils.returnSuccess(groupInformationService.findGroupSubmitList(userId));
    }
}
