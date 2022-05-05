package com.life.decision.support.controller;

import com.life.decision.support.dto.SysDictDto;
import com.life.decision.support.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Controller
@RequestMapping("/sysDict")
public class SysDictController {
    @Autowired
    ISysDictService sysDictService;

    @PostMapping("universalDictionary")
    @ResponseBody
    public List<SysDictDto> universalDictionary(@RequestBody SysDictDto sysDictDto){
        return sysDictService.dictList(sysDictDto.getLabel());
    }
}
