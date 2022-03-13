package com.life.decision.support.controller;


import com.life.decision.support.mapper.UserInformationMapper;
import com.life.decision.support.pojo.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
@RequestMapping("/userInformation")
public class UserInformationController {

    @Autowired
    private UserInformationMapper userInformationMapper;

    @RequestMapping("userList")
    @ResponseBody
    public Object userList() {
        return userInformationMapper.findList(new UserInformation());
    }

}
