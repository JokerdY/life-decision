package com.life.decision.support.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.pojo.PassWordChangeDto;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.IUserInformationService;
import com.life.decision.support.utils.ResultUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Api(tags = "用户信息接口")
@Slf4j
@Controller
@RequestMapping("/userInformation")
public class UserInformationController {

    @Autowired
    private IUserInformationService userInformationService;

    @PostMapping("page")
    @ResponseBody
    public Object userPage(@RequestBody UserInformationDto userInformationDto) {
        List<UserInformationDto> list = userInformationService.findList(userInformationDto);
        return ResultUtils.returnPage(new PageInfo<>(list));
    }

    @PostMapping("getMsg")
    @ResponseBody
    public Object getUserMsg(@RequestBody UserInformation userInformation) {
        if (StrUtil.isBlank(userInformation.getId())) {
            return ResultUtils.returnError("查询失败，用户账号信息为空");
        }
        return ResultUtils.returnSuccess(userInformationService.getUserMsg(userInformation));
    }

    @PostMapping("changePassword")
    @ResponseBody
    public Object changePassword(@RequestBody PassWordChangeDto dto) {
        if (StrUtil.isNotBlank(dto.getNewPwd()) && StrUtil.isNotBlank(dto.getOldPwd()) && StrUtil.isNotBlank(dto.getUserId())) {
            try {
                UserInformation temp = new UserInformation();
                temp.setPassword(dto.getOldPwd());
                temp.setId(dto.getUserId());
                UserInformationDto adminUser = userInformationService.getAdminUser(temp);
                if (adminUser == null || !adminUser.getPassword().equals(temp.getPassword())) {
                    return ResultUtils.returnError("旧密码错误，请确认密码");
                }
                if (userInformationService.changePassword(dto)) {
                    return ResultUtils.returnSuccess("密码重置成功，请重新登录");
                }
            } catch (Exception e) {
                log.error("密码重置异常", e);
                return ResultUtils.returnError("密码重置失败，请联系管理员，异常信息为：" + e.getMessage());
            }
        }
        return ResultUtils.returnError("账号或密码为空");
    }

    @PostMapping("editPersonalInformation")
    @ResponseBody
    public Object editPersonalInformation(@RequestBody UserInformation userInformation) {
        if (StrUtil.isBlank(userInformation.getTelphoneNum())) {
            return ResultUtils.returnError("账号信息为空");
        }
        try {
            if (userInformationService.editPersonalInformation(userInformation)) {
                return ResultUtils.returnSuccess("修改个人信息成功");
            }
        } catch (Exception e) {
            log.error("修改个人信息失败", e);
            return ResultUtils.returnError("修改个人信息失败，后台异常请联系管理员,错误信息：" + e.getMessage());
        }
        return ResultUtils.returnError();
    }

    @PostMapping("login")
    @ResponseBody
    public Object login(@RequestBody UserInformation userInformation) {
        if (StrUtil.isBlank(userInformation.getTelphoneNum()) || StrUtil.isBlank(userInformation.getPassword())) {
            return ResultUtils.returnError("账号密码为空");
        }
        if (userInformationService.verifyIdentified(userInformation)) {
            UserInformationDto userMsg = userInformationService.getUserMsg(userInformation);
            Map<String, Object> result = new HashMap<>(2);
            result.put("token", userMsg.getId());
            if (userInformation.getTelphoneNum().endsWith(userInformation.getPassword()) && userInformation.getPassword().length() == 6) {
                result.put("msg", "当前密码安全度较低，请修改密码！");
                return ResultUtils.returnSuccess(result);
            }
            return ResultUtils.returnSuccess(result);
        }
        return ResultUtils.returnError("账号或密码错误");
    }

    @PostMapping("register")
    @ResponseBody
    public Object register(@RequestBody UserInformation userInformation) {
        userInformation.setAdminEnable(0);
        try {
            if (userInformationService.isExist(userInformation)) {
                return ResultUtils.returnError("用户信息已存在");
            }
            userInformationService.insertUser(userInformation);
        } catch (Exception e) {
            log.error("注册失败", e);
            return ResultUtils.returnError("后台异常请联系管理员,错误信息：" + e.getMessage());
        }
        return ResultUtils.returnSuccess(userInformationService.getUserMsg(userInformation));
    }

    @PostMapping("isExist")
    @ResponseBody
    public Object isExist(@RequestBody UserInformation userInformation) {
        if (userInformationService.isExist(userInformation)) {
            return ResultUtils.returnError();
        }
        return ResultUtils.returnSuccess();
    }


    @PostMapping("resetPassword")
    @ResponseBody
    public Object resetPassword(@RequestBody UserInformation userInformation) {
        if (userInformationService.isExist(userInformation)) {
            try {
                userInformationService.resetPassword(userInformation, 6);
            } catch (Exception e) {
                log.error("重置密码失败", e);
                return ResultUtils.returnError("后台异常请联系管理员,错误信息：" + e.getMessage());
            }
            return ResultUtils.returnSuccess("重置成功！密码为手机尾号后六位");
        }
        return ResultUtils.returnError("当前用户信息不存在");
    }
}
