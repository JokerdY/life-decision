package com.life.decision.support.controller;

import com.life.decision.support.service.impl.PsychologyResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("script")
public class ScriptController {

    @Autowired
    PsychologyResultServiceImpl psychologyResultService;

    @RequestMapping("physical")
    @ResponseBody
    public String physical(@RequestParam("token") String token) {
        if ("gerqqfgewr".equals(token)) {
            return "更新数据条数:" + psychologyResultService.uploadScript() + "条";
        }
        return "秘钥错误";
    }
}
