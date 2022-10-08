package com.life.decision.support.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.life.decision.support.enums.ModuleType;
import com.life.decision.support.pojo.ModuleAccessDetails;
import com.life.decision.support.pojo.UrlAccessDetails;
import com.life.decision.support.service.impl.ModuleAccessDetailsService;
import com.life.decision.support.service.impl.UrlAccessDetailsService;
import com.life.decision.support.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("access")
public class AccessController {
    @Autowired
    UrlAccessDetailsService urlAccessDetailsService;
    @Autowired
    ModuleAccessDetailsService moduleAccessDetailsService;

    @RequestMapping("url")
    @ResponseBody
    public Object url(@RequestBody UrlAccessDetails details) {
        details.setId(IdUtil.fastSimpleUUID());
        if (StrUtil.isBlank(details.getUserId()) ||
                StrUtil.isBlank(details.getUrl()) ||
                StrUtil.isBlank(details.getType())) {
            return ResultUtils.returnError("参数不完整");
        }
        if (urlAccessDetailsService.saveApi(details) > 0) {
            return ResultUtils.returnSuccess();
        } else {
            return ResultUtils.returnError("插入条目数为0");
        }
    }

    @RequestMapping("visit")
    @ResponseBody
    public Object visit(@RequestBody ModuleAccessDetails details) {
        if (StrUtil.isBlank(details.getUserId())) {
            return ResultUtils.returnError("参数不完整");
        }
        if (moduleAccessDetailsService.saveApi(ModuleType.ACCESS, "visit", details.getUserId()) > 0) {
            return ResultUtils.returnSuccess();
        } else {
            return ResultUtils.returnError("插入条目数为0");
        }
    }
}
