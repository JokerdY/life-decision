package com.life.decision.support.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.life.decision.support.bo.SeriesVo;
import com.life.decision.support.dto.DataAnalysisDto;
import com.life.decision.support.enums.ResultEnum;
import com.life.decision.support.pojo.PsychologicalOutcome;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.PsychologicalOutcomeService;
import com.life.decision.support.service.QuestionnaireMsgQueryService;
import com.life.decision.support.service.impl.QuestionAnswerServiceImpl;
import com.life.decision.support.service.impl.QuestionnaireGroupInformationServiceImpl;
import com.life.decision.support.service.impl.UserInformationServiceImpl;
import com.life.decision.support.utils.ResultUtils;
import com.life.decision.support.vo.DataAnalysisVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/data/analysis/app")
public class DataAnalysisAppController {

    @Autowired
    QuestionnaireMsgQueryService questionnaireMsgQueryService;
    @Autowired
    QuestionnaireGroupInformationServiceImpl groupInformationService;
    @Autowired
    QuestionAnswerServiceImpl questionAnswerService;
    @Autowired
    UserInformationServiceImpl userInformationService;
    @Autowired
    PsychologicalOutcomeService psychologicalOutcomeService;

    @RequestMapping("date/list")
    public Map<String, Object> dateList(@RequestBody UserInformation dto) {
        if (StrUtil.isBlank(dto.getUserId())) {
            return ResultUtils.returnError("用户id缺失");
        }
        QuestionnaireGroupInformation entity = new QuestionnaireGroupInformation();
        entity.setUserId(dto.getUserId());
        List<QuestionnaireGroupInformation> list1 = groupInformationService.findList(entity);
        PsychologicalOutcome temp = new PsychologicalOutcome();
        temp.setUserId(dto.getUserId());
        List<PsychologicalOutcome> list2 = psychologicalOutcomeService.findList(temp);
        JSONArray result = new JSONArray();
        LocalDate minDate1 = list1.stream().map(s -> s.getCreateDate().toLocalDate()).min(Comparator.naturalOrder()).orElse(null);
        LocalDate maxDate1 = list1.stream().map(s -> s.getUpdateDate().toLocalDate()).max(Comparator.naturalOrder()).orElse(null);
        LocalDate minDate2 = list2.stream().map(s -> s.getCreateDate().toLocalDate()).min(Comparator.naturalOrder()).orElse(null);
        LocalDate maxDate2 = list2.stream().map(s -> s.getUpdateDate().toLocalDate()).max(Comparator.naturalOrder()).orElse(null);
        if (minDate1 != null && maxDate1 != null) {
            if (minDate2 == null) {
                result.add(minDate1);
            } else {
                result.add(minDate1.compareTo(minDate2) > 0 ? minDate2 : minDate1);
            }
            if (maxDate2 == null) {
                result.add(maxDate1);
            } else {
                result.add(maxDate1.compareTo(maxDate2) > 0 ? maxDate1 : maxDate2);
            }
        }
        return ResultUtils.returnSuccess(result);
    }

    @RequestMapping("blood/pressure/list")
    public Map<String, Object> bloodPressureList(@RequestBody DataAnalysisDto dto) {
        if (StrUtil.isBlank(dto.getUserId())) {
            return ResultUtils.returnError("用户id缺失");
        } else {
            DataAnalysisVo result = new DataAnalysisVo();
            loadDate(dto);
            if (StrUtil.isBlank(dto.getStartDate()) || StrUtil.isBlank(dto.getEndDate())) {
                return ResultUtils.returnSuccess(result);
            }
            List<QuestionAnswer> list = questionnaireMsgQueryService.getAnswerListByDate(dto, "175");
            List<String> highData = new ArrayList<>();
            List<String> lowData = new ArrayList<>();
            List<String> xAxis = new ArrayList<>();
            for (QuestionAnswer answer : list) {
                String[] blood = answer.getComment().split("/");
                highData.add(blood[0]);
                lowData.add(blood[1]);
                xAxis.add(answer.getCreateDate().format(QuestionnaireMsgQueryService.FORMATTER));
            }
            SeriesVo highResult = new SeriesVo("高压", highData);
            SeriesVo lowResult = new SeriesVo("低压", lowData);
            List<SeriesVo> vos = new ArrayList<>();
            vos.add(highResult);
            vos.add(lowResult);
            result.setDataList(vos);
            result.setXAxis(xAxis);
            return ResultUtils.returnSuccess(result);
        }
    }

    @RequestMapping("weight/list")
    public Map<String, Object> weightList(@RequestBody DataAnalysisDto dto) {
        if (StrUtil.isBlank(dto.getUserId())) {
            return ResultUtils.returnError("用户id缺失");
        } else {
            DataAnalysisVo result = new DataAnalysisVo();
            loadDate(dto);
            if (StrUtil.isBlank(dto.getStartDate()) || StrUtil.isBlank(dto.getEndDate())) {
                return ResultUtils.returnSuccess(result);
            }
            List<String> xAxis = new ArrayList<>();
            List<SeriesVo> vos = new ArrayList<>();
            List<String> data = new ArrayList<>();
            questionnaireMsgQueryService.getAnswerListByDate(dto, "173")
                    .forEach(answer -> {
                        data.add(answer.getComment());
                        xAxis.add(answer.getCreateDate().format(QuestionnaireMsgQueryService.FORMATTER));
                    });
            vos.add(new SeriesVo("体重", data));
            result.setDataList(vos);
            result.setXAxis(xAxis);
            return ResultUtils.returnSuccess(result);
        }
    }

    @RequestMapping("bmi/list")
    public Map<String, Object> bmiList(@RequestBody DataAnalysisDto dto) {
        if (StrUtil.isBlank(dto.getUserId())) {
            return ResultUtils.returnError("用户id缺失");
        } else {
            DataAnalysisVo result = new DataAnalysisVo();
            loadDate(dto);
            if (StrUtil.isBlank(dto.getStartDate()) || StrUtil.isBlank(dto.getEndDate())) {
                return ResultUtils.returnSuccess(result);
            }
            List<String> xAxis = new ArrayList<>();
            List<SeriesVo> vos = new ArrayList<>();
            List<String> data = new ArrayList<>();
            List<QuestionAnswer> heightList = questionnaireMsgQueryService.getAnswerListByDate(dto, "172");
            List<QuestionAnswer> weightList = questionnaireMsgQueryService.getAnswerListByDate(dto, "173");
            for (int i = 0; i < Math.min(heightList.size(), weightList.size()); i++) {
                QuestionAnswer heightAnswer = heightList.get(i);
                QuestionAnswer weightAnswer = weightList.get(i);
                data.add(String.format("%.1f",userInformationService.getBmi(heightAnswer.getComment(), weightAnswer.getComment())));
                xAxis.add(weightAnswer.getCreateDate().format(QuestionnaireMsgQueryService.FORMATTER));
            }
            vos.add(new SeriesVo("BMI", data));
            result.setDataList(vos);
            result.setXAxis(xAxis);
            return ResultUtils.returnSuccess(result);
        }
    }

    @RequestMapping("psychological/list")
    public Map<String, Object> psychologicalList(@RequestBody DataAnalysisDto dto) {
        if (StrUtil.isBlank(dto.getUserId())) {
            return ResultUtils.returnError("用户id缺失");
        } else {
            DataAnalysisVo result = new DataAnalysisVo();
            loadDate(dto);
            if (StrUtil.isBlank(dto.getStartDate()) || StrUtil.isBlank(dto.getEndDate())) {
                return ResultUtils.returnSuccess(result);
            }
            List<String> xAxis = new ArrayList<>();
            List<SeriesVo> vos = new ArrayList<>();
            List<String> pressureData = new ArrayList<>();
            List<String> anxietyData = new ArrayList<>();
            List<String> depressionData = new ArrayList<>();

            PsychologicalOutcome temp = new PsychologicalOutcome();
            temp.setUserId(dto.getUserId());
            List<PsychologicalOutcome> list = psychologicalOutcomeService.findList(temp);
            for (PsychologicalOutcome outcome : list) {
                pressureData.add(outcome.getPressure());
                anxietyData.add(outcome.getAnxiety());
                depressionData.add(outcome.getDepression());
                xAxis.add(outcome.getCreateDate().format(QuestionnaireMsgQueryService.FORMATTER));
            }
            vos.add(new SeriesVo("压力", pressureData));
            vos.add(new SeriesVo("焦虑", anxietyData));
            vos.add(new SeriesVo("抑郁", depressionData));
            result.setDataList(vos);
            result.setXAxis(xAxis);
            return ResultUtils.returnSuccess(result);
        }
    }


    private void loadDate(DataAnalysisDto dto) {
        if (StrUtil.isBlank(dto.getEndDate()) || StrUtil.isBlank(dto.getStartDate())) {
            UserInformation temp = new UserInformation();
            temp.setUserId(dto.getUserId());
            Map<String, Object> tempMap = dateList(temp);
            Object o = tempMap.get(ResultEnum.DATA.getMsg());
            if (o != null) {
                JSONArray array = (JSONArray) o;
                dto.setStartDate(array.getStr(0));
                dto.setEndDate(array.getStr(1));
            }
        }
    }
}
