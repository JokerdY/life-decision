package com.life.decision.support.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.dto.QuestionnaireInformationUserDto;
import com.life.decision.support.excel.ExcelFactory;
import com.life.decision.support.excel.ExcelHeader;
import com.life.decision.support.pojo.QuestionnaireInformation;
import com.life.decision.support.service.*;
import com.life.decision.support.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Slf4j
@Controller
@RequestMapping("/questionnaireInformation")
public class QuestionnaireInformationController {

    @Autowired
    IQuestionnaireInformationService questionnaireInformationService;
    @Autowired
    IQuestionnaireSubmitInformationService submitInformationService;
    @Autowired
    IQuestionInformationService questionInformationService;
    @Autowired
    IUserInformationService userInformationService;
    @Autowired
    IQuestionAnswerService answerService;

    @PostMapping("adminPage")
    @ResponseBody
    public Object questionnaireInfoPage(@RequestBody QuestionnaireInformation questionnaireInformation) {
        try {
            return ResultUtils.returnPage(new PageInfo<>(questionnaireInformationService.findList(questionnaireInformation)));
        } catch (Exception e) {
            log.error("问卷概要获取失败", e);
            return ResultUtils.returnError("信息获取错误，请联系管理员:" + e.getMessage());
        }
    }

    @RequestMapping("exportAnswer")
    @ResponseBody
    public Object exportAnswer(HttpServletResponse response,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate) {
        try {
            List<String> groupIdsByDate = submitInformationService.getGroupIdsByDate(startDate, endDate);
            // 先获取五张问卷的header
            List<QuestionInformationDto> questionInformationDtoList = questionInformationService.listById(null);
            ExcelFactory entity = new ExcelFactory.Builder()
                    .build(questionInformationDtoList,
                            groupIdsByDate,
                            submitInformationService,
                            answerService,
                            userInformationService);
            ExcelHeader excelHeader = entity.getHeader();

            String fileName = URLEncoder.encode("正常高值血压人群健康生活方式调查问卷", "UTF-8").replaceAll("\\+", "%20");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .autoCloseStream(Boolean.FALSE)
                    // 这里放入动态头
                    .head(excelHeader.getHead())
                    .sheet("正常高值血压人群健康生活方式调查问卷")
                    .registerWriteHandler(excelHeader.getHorizontalCellStyleStrategy())
                    .doWrite(entity.getData().getRealData());
            return ResultUtils.returnSuccess("导出成功");
        } catch (Exception e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            log.error("导出报错", e);
        }
        return ResultUtils.returnError("导出失败");
    }


    @PostMapping("page")
    @ResponseBody
    public Object page(@RequestBody QuestionnaireInformationUserDto dto) {
        try {
            return ResultUtils.returnPage(new PageInfo<>(questionnaireInformationService.findListInUser(dto)));
        } catch (Exception e) {
            log.error("问卷概要信息获取失败", e);
            return ResultUtils.returnError("问卷概要信息获取失败，请联系管理员：" + e.getMessage());
        }
    }

}
