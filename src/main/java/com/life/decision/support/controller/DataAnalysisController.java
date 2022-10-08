package com.life.decision.support.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.life.decision.support.bo.SeriesVo;
import com.life.decision.support.dto.*;
import com.life.decision.support.enums.ModuleType;
import com.life.decision.support.pojo.PsychologicalOutcome;
import com.life.decision.support.service.PsychologicalOutcomeService;
import com.life.decision.support.service.impl.*;
import com.life.decision.support.utils.ResultUtils;
import com.life.decision.support.vo.DataAnalysisVo;
import com.life.decision.support.vo.PsychologicalOutcomeVo;
import com.life.decision.support.vo.DataCountByMouthVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/data/analysis/pc")
public class DataAnalysisController {

    @Autowired
    UserInformationServiceImpl userInformationService;
    @Autowired
    QuestionAnswerServiceImpl answerService;
    @Autowired
    PsychologicalOutcomeService psychologicalOutcomeService;
    @Autowired
    ModuleAccessDetailsService moduleAccessDetailsService;
    @Autowired
    UrlAccessDetailsService urlAccessDetailsService;
    @Autowired
    QuestionnaireGroupInformationServiceImpl groupInformationService;

    @ResponseBody
    @RequestMapping("basic")
    public Object basic(@RequestBody DataAnalysisDto dto) {
        JSONObject result = new JSONObject();

        List<UserInformationDto> user = userInformationService.findAllList(new UserInformationDto());
        putUserResult(user, UserInformationDto::getSexDto, result, "sex");

        putUserResult(user, UserInformationDto::getMaritalDto, result, "marital");

        putUserResult(user, UserInformationDto::getEducationalLevelDto, result, "educational");

        return ResultUtils.returnSuccess(result);
    }

    private void putUserResult(List<UserInformationDto> user, Function<UserInformationDto, String> getFunc, JSONObject result, String keyName) {
        Map<String, List<String>> map = user
                .stream()
                .map(getFunc)
                .map(s -> {
                    if (s == null) {
                        return "未知";
                    }
                    return s;
                })
                .collect(Collectors.groupingBy(s -> s));

        JSONArray data = new JSONArray();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            JSONObject obj = new JSONObject();
            obj.putOpt("name", entry.getKey());
            obj.putOpt("value", entry.getValue().size());
            data.add(obj);
        }
        result.putOpt(keyName, data);
    }

    /**
     * 锻炼
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("exercise")
    public Object exercise(@RequestBody DataAnalysisDto dto) {
        JSONObject result = getAnswerResult("17");
        return ResultUtils.returnSuccess(result);
    }

    /**
     * 吸烟
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("smoke")
    public Object smoke(@RequestBody DataAnalysisDto dto) {
        List<Map<String, List<AnswerDto>>> mapList = new ArrayList<>();
        mapList.add(loadAnswerMap("19"));
        Map<String, List<AnswerDto>> map2 = loadAnswerMap("22");
        List<AnswerDto> change = map2.get("未戒烟或从不吸烟");
        map2.put("从不吸烟", change);
        map2.remove("未戒烟或从不吸烟");
        mapList.add(map2);
        JSONObject result = getAnswerResult(mapList);
        return ResultUtils.returnSuccess(result);
    }

    /**
     * 饮酒
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("drinking")
    public Object drinking(@RequestBody DataAnalysisDto dto) {
        List<Map<String, List<AnswerDto>>> mapList = new ArrayList<>();
        mapList.add(loadAnswerMap("24"));
        Map<String, List<AnswerDto>> map2 = loadAnswerMap("26");
        List<AnswerDto> change = map2.get("未戒酒或从不饮酒");
        map2.put("从不饮酒", change);
        map2.remove("未戒酒或从不饮酒");
        mapList.add(map2);
        JSONObject result = getAnswerResult(mapList);
        return ResultUtils.returnSuccess(result);
    }

    /**
     * 心理
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("physicalActivities")
    public Object physicalActivities(@RequestBody DataAnalysisDto dto) {
        List<PsychologicalOutcome> list = psychologicalOutcomeService.selectLatestByEntity(new PsychologicalOutcome());
        List<PsychologicalOutcomeVo> anxiety = new ArrayList<>();
        for (PsychologicalOutcome outcome : list) {
            PsychologicalOutcomeVo temp = new PsychologicalOutcomeVo();
            BeanUtil.copyProperties(outcome, temp);
            int a = Integer.parseInt(temp.getAnxiety());
            String name = "情绪良好";
            if (a >= 5 && a <= 9) {
                name = "轻度焦虑情绪";
            } else if (a >= 10 && a <= 14) {
                name = "中度焦虑情绪";
            } else if (a >= 15) {
                name = "重度焦虑情绪";
            }
            temp.setName(name);
            anxiety.add(temp);
        }

        List<PsychologicalOutcomeVo> pressure = new ArrayList<>();
        for (PsychologicalOutcome outcome : list) {
            PsychologicalOutcomeVo temp = new PsychologicalOutcomeVo();
            BeanUtil.copyProperties(outcome, temp);
            int p = Integer.parseInt(temp.getPressure());
            String name = "无压力症状";
            if (p >= 25) {
                name = "存在压力";
            }
            temp.setName(name);
            pressure.add(temp);
        }

        List<PsychologicalOutcomeVo> depression = new ArrayList<>();
        for (PsychologicalOutcome outcome : list) {
            PsychologicalOutcomeVo temp = new PsychologicalOutcomeVo();
            BeanUtil.copyProperties(outcome, temp);
            int d = Integer.parseInt(temp.getDepression());
            String name = "无抑郁情绪";
            if (d >= 10) {
                name = "抑郁情绪";
            }
            temp.setName(name);
            depression.add(temp);
        }
        JSONObject result = new JSONObject();
        result.putOpt("anxiety", getPsArray(anxiety));
        result.putOpt("pressure", getPsArray(pressure));
        result.putOpt("depression", getPsArray(depression));
        return ResultUtils.returnSuccess(result);
    }

    private JSONArray getPsArray(List<PsychologicalOutcomeVo> anxiety) {
        JSONArray anxietyArray = new JSONArray();
        anxiety.stream().collect(Collectors.groupingBy(PsychologicalOutcomeVo::getName))
                .forEach((k, v) -> {
                    JSONObject temp = new JSONObject();
                    temp.putOpt("name", k);
                    temp.putOpt("value", v.size());
                    anxietyArray.put(temp);
                });
        return anxietyArray;
    }

    private JSONObject getAnswerResult(String questionId) {
        JSONObject result = new JSONObject();
        Map<String, List<AnswerDto>> map = loadAnswerMap(questionId);
        return getResult(result, map);
    }

    private JSONObject getAnswerResult(List<Map<String, List<AnswerDto>>> mapList) {
        JSONObject result = new JSONObject();
        Map<String, List<AnswerDto>> queryMap = new HashMap<>();
        for (Map<String, List<AnswerDto>> map : mapList) {
            for (Map.Entry<String, List<AnswerDto>> entry : map.entrySet()) {
                String key = entry.getKey();
                if (queryMap.containsKey(key)) {
                    List<AnswerDto> answerDtos = queryMap.get(key);
                    answerDtos.addAll(entry.getValue());
                } else {
                    queryMap.put(key, entry.getValue());
                }
            }
        }
        return getResult(result, queryMap);
    }

    private Map<String, List<AnswerDto>> loadAnswerMap(String questionId) {
        return answerService.selectAnswerByUser(questionId)
                .stream().peek(s -> {
                    // 如果可选 则 替换结果
                    if ("1".equals(s.getFillEnabled())) {
                        s.setOptionValue(String.format(s.getOptionValue(), s.getComment()));
                    }
                }).collect(Collectors.groupingBy(AnswerDto::getOptionValue));
    }

    private JSONObject getResult(JSONObject result, Map<String, List<AnswerDto>> map) {
        JSONArray data = new JSONArray();
        for (Map.Entry<String, List<AnswerDto>> entry : map.entrySet()) {
            JSONObject obj = new JSONObject();
            obj.putOpt("name", entry.getKey());
            obj.putOpt("value", entry.getValue().size());
            data.add(obj);
        }
        result.putOpt("data", data);
        return result;
    }

    @RequestMapping("module")
    @ResponseBody
    public Map<String, Object> module(@RequestBody ModuleAccessDetailsDto dto) {
        return ResultUtils.returnPage(moduleAccessDetailsService.pageVo(dto));
    }

    @RequestMapping("module/name/list")
    @ResponseBody
    public List<JSONObject> moduleNameList() {
        return Arrays.stream(ModuleType.values()).map(s -> {
            JSONObject temp = new JSONObject();
            temp.putOpt("id", s.getType());
            temp.putOpt("name", s.getName());
            return temp;
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        //(1食谱 2运动 3心理 4中医)
        StringBuilder s = new StringBuilder("insert into module_access_details (id, type, user_id, create_date, api) values ");
        for (int i = 0; i < 943; i++) {
            s.append("('").append(IdUtil.fastSimpleUUID()).append("','3','123','2022-09-29','/history'),\n");
        }
        for (int i = 0; i < 142; i++) {
            s.append("('").append(IdUtil.fastSimpleUUID()).append("','2','123','2022-09-29','/history'),\n");
        }
        for (int i = 0; i < 132; i++) {
            s.append("('").append(IdUtil.fastSimpleUUID()).append("','4','123','2022-09-29','/history'),\n");
        }
        for (int i = 0; i < 253; i++) {
            s.append("('").append(IdUtil.fastSimpleUUID()).append("','1','123','2022-09-29','/history'),\n");
        }
        System.out.println(s);
    }

    @RequestMapping("url/access")
    @ResponseBody
    public Map<String, Object> urlAccess(@RequestBody UrlAccessDetailsDto dto) {
        return ResultUtils.returnPage(urlAccessDetailsService.pageCountVo(dto));
    }

    @RequestMapping("url/access/name/list")
    @ResponseBody
    public List<String> urlAccessNameList() {
        return urlAccessDetailsService.distinctNameList();
    }

    @RequestMapping("total/access/num")
    @ResponseBody
    public Map<String, Object> totalAccessNum(@RequestBody DataAnalysisDto dto) {
        List<DataCountByMouthVo> list = moduleAccessDetailsService.findTotalAccessVo(dto.getStartDate(), dto.getEndDate());
        DataAnalysisVo result = getSingleMouthDataVo(list);
        return ResultUtils.returnSuccess(result);
    }

    @RequestMapping("visitor/num")
    @ResponseBody
    public Map<String, Object> visitorNum(@RequestBody DataAnalysisDto dto) {
        List<DataCountByMouthVo> list = userInformationService.findUserRegisterVo(dto.getStartDate(), dto.getEndDate());
        DataAnalysisVo result = getSingleMouthDataVo(list);
        return ResultUtils.returnSuccess(result);
    }

    @RequestMapping("submit/num")
    @ResponseBody
    public Map<String, Object> submitNum(@RequestBody DataAnalysisDto dto) {
        List<DataCountByMouthVo> list = groupInformationService.findUserRegisterVo(dto.getStartDate(), dto.getEndDate());
        DataAnalysisVo result = getSingleMouthDataVo(list);
        return ResultUtils.returnSuccess(result);
    }

    private DataAnalysisVo getSingleMouthDataVo(List<DataCountByMouthVo> list) {
        DataAnalysisVo result = new DataAnalysisVo();
        List<String> x = new ArrayList<>();
        List<SeriesVo> vos = new ArrayList<>();
        list.sort(Comparator.comparing(s -> DateUtil.parse(s.getDate(), "yyyy-MM")));
        List<String> data = new ArrayList<>();
        for (DataCountByMouthVo vo : list) {
            data.add(vo.getCount() + "");
            x.add(vo.getDate());
        }
        vos.add(new SeriesVo("line", data));
        result.setXAxis(x);
        result.setDataList(vos);
        return result;
    }

}
