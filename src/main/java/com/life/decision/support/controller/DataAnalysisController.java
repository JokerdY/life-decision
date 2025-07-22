package com.life.decision.support.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.bo.SeriesVo;
import com.life.decision.support.dto.*;
import com.life.decision.support.enums.ModuleType;
import com.life.decision.support.mapper.OptionInformationMapper;
import com.life.decision.support.pojo.OptionInformation;
import com.life.decision.support.pojo.PsychologicalOutcome;
import com.life.decision.support.pojo.PsychologyResult;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.service.PsychologicalOutcomeService;
import com.life.decision.support.service.impl.*;
import com.life.decision.support.utils.ResultUtils;
import com.life.decision.support.vo.DataAnalysisPieVo;
import com.life.decision.support.vo.DataAnalysisVo;
import com.life.decision.support.vo.DataCountByMouthVo;
import com.life.decision.support.vo.PsychologicalOutcomeVo;
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
    OptionInformationMapper optionInformationMapper;
    @Autowired
    QuestionInformationServiceImpl questionInformationService;
    @Autowired
    PsychologicalOutcomeService psychologicalOutcomeService;
    @Autowired
    PsychologyResultServiceImpl psychologyResultService;
    @Autowired
    ModuleAccessDetailsService moduleAccessDetailsService;
    @Autowired
    UrlAccessDetailsService urlAccessDetailsService;
    @Autowired
    QuestionnaireGroupInformationServiceImpl groupInformationService;
    @Autowired
    SysDictServiceImpl dictService;

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
        JSONObject result = getAnswerResult("17", dto);
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
        mapList.add(loadSingleAnswerMap("19", dto));
        Map<String, List<AnswerDto>> map2 = loadSingleAnswerMap("22", dto);
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
        mapList.add(loadSingleAnswerMap("24", dto));
        Map<String, List<AnswerDto>> map2 = loadSingleAnswerMap("26", dto);
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

    private JSONObject getAnswerResult(String questionId, DataAnalysisDto dto) {
        JSONObject result = new JSONObject();
        Map<String, List<AnswerDto>> map = loadSingleAnswerMap(questionId, dto);
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

    private Map<String, List<AnswerDto>> loadSingleAnswerMap(String questionId, DataAnalysisDto dataAnalysisDto) {
        return answerService.selectSingleAnswerByUser(questionId, dataAnalysisDto)
                .stream().peek(s -> {
                    // 如果可选 则 替换结果
                    if ("1".equals(s.getFillEnabled())) {
                        s.setOptionValue(String.format(s.getOptionValue(), s.getComment()));
                    }
                }).collect(Collectors.groupingBy(AnswerDto::getOptionValue));
    }

    private Map<String, List<AnswerDto>> selectMultipleAnswerByUser(String questionId, DataAnalysisDto dataAnalysisDto) {
        return answerService.selectMultipleAnswerByUser(questionId, dataAnalysisDto)
                .stream().collect(Collectors.groupingBy(AnswerDto::getOptionValue));
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
        DataAnalysisVo result = getSingleMouthDataVo(list, "访问量");
        return ResultUtils.returnSuccess(result);
    }

    @RequestMapping("visitor/num")
    @ResponseBody
    public Map<String, Object> visitorNum(@RequestBody DataAnalysisDto dto) {
        List<DataCountByMouthVo> list = userInformationService.findUserRegisterVo(dto.getStartDate(), dto.getEndDate());
        DataAnalysisVo result = getSingleMouthDataVo(list, "用户量");
        return ResultUtils.returnSuccess(result);
    }

    @RequestMapping("submit/num")
    @ResponseBody
    public Map<String, Object> submitNum(@RequestBody DataAnalysisDto dto) {
        List<DataCountByMouthVo> list = groupInformationService.findUserRegisterVo(dto.getStartDate(), dto.getEndDate());
        DataAnalysisVo result = getSingleMouthDataVo(list, "完成次数");
        return ResultUtils.returnSuccess(result);
    }

    private DataAnalysisVo getSingleMouthDataVo(List<DataCountByMouthVo> list, String name) {
        DataAnalysisVo result = new DataAnalysisVo();
        List<String> x = new ArrayList<>();
        List<SeriesVo> vos = new ArrayList<>();
        list.sort(Comparator.comparing(s -> DateUtil.parse(s.getDate(), "yyyy-MM")));
        List<String> data = new ArrayList<>();
        for (DataCountByMouthVo vo : list) {
            data.add(vo.getCount() + "");
            x.add(vo.getDate());
        }
        vos.add(new SeriesVo(name, data));
        result.setXAxis(x);
        result.setDataList(vos);
        return result;
    }


    /**
     * 过敏史
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("allergyHistory")
    public Object allergyHistory(@RequestBody DataAnalysisDto dto) {
        Map<String, List<AnswerDto>> map = loadSingleAnswerMap("1", dto);
        DataAnalysisPieVo vo = DataAnalysisPieVo.build(map);
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 疾病既往史
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("pastHistoryOfDisease")
    public Object pastHistoryOfDisease(@RequestBody DataAnalysisDto dto) {
        Map<String, List<AnswerDto>> map = loadSingleAnswerMap("7", dto);
        DataAnalysisPieVo vo = DataAnalysisPieVo.build(map);
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 手术既往史
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("pastHistoryOfSurgery")
    public Object pastHistoryOfSurgery(@RequestBody DataAnalysisDto dto) {
        Map<String, List<AnswerDto>> map = loadSingleAnswerMap("9", dto);
        DataAnalysisPieVo vo = DataAnalysisPieVo.build(map);
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 症状
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("symptom")
    public Object symptom(@RequestBody DataAnalysisDto dto) {
        Map<String, List<AnswerDto>> map = loadSingleAnswerMap("13", dto);
        DataAnalysisPieVo vo = DataAnalysisPieVo.build(map);
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 饮食习惯
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("eatingHabits")
    public Object eatingHabits(@RequestBody DataAnalysisDto dto) {
        List<String> habitsList = Arrays.asList("208", "207", "206", "205", "204", "203", "202",
                "201", "200", "199", "198", "197", "196", "195", "194",
                "193", "192", "191", "190", "189", "188", "187");
        DataAnalysisVo dataAnalysisVo = new DataAnalysisVo();
        List<SeriesVo> dataList = new ArrayList<>();
        List<String> yAxis = new ArrayList<>();
        List<Map<String, List<AnswerDto>>> mapList = new ArrayList<>();
        for (String id : habitsList) {
            QuestionInformationDto informationDto = questionInformationService.findDto(id);
            Map<String, List<AnswerDto>> map = loadSingleAnswerMap(id, dto);
            String name = informationDto.getQuestionName().substring(4);
            mapList.add(map);
            yAxis.add(name);
        }
        OptionInformation record = new OptionInformation();
        record.setQuestionId("197");
        List<String> optionValue = optionInformationMapper.findList(record)
                .stream().map(OptionInformation::getOptionValue)
                .collect(Collectors.toList());
        for (String option : optionValue) {
            List<String> data = new ArrayList<>();
            for (Map<String, List<AnswerDto>> map : mapList) {
                data.add(map.getOrDefault(option, new ArrayList<>()).size() + "");
            }
            dataList.add(new SeriesVo(option, data));
        }
        dataAnalysisVo.setYAxis(yAxis);
        dataAnalysisVo.setDataList(dataList);
        return ResultUtils.returnSuccess(dataAnalysisVo);
    }


    private DataAnalysisPieVo buildPieVo(List<AnswerDto> answerDtos, List<String> axis, String defaultKey,
                                         Function<AnswerDto, String> getKey) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String axi : axis) {
            map.put(axi, 0);
        }
        for (AnswerDto answerDto : answerDtos) {
            try {
                String key = getKey.apply(answerDto);
                map.put(key, map.get(key) + 1);
            } catch (Exception e) {
                map.put(defaultKey, map.get(defaultKey) + 1);
                log.error("数据异常,defaultKey:{}", defaultKey, e);
            }
        }
        return DataAnalysisPieVo.build(map);
    }

    /**
     * 血压
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("bloodPressure")
    public Object bloodPressure(@RequestBody DataAnalysisDto dto) {
        List<AnswerDto> answerDtos = answerService.selectSingleAnswerByUser("175", dto);
//        正常血压：收缩压＜120mmHg和舒张压＜80mmHg；
//        正常高值血压：收缩压120-139mmHg和（或）舒张压80-89mmHg；
//        高血压：收缩压≥140mmHg和（或）舒张压≥90mmHg
        String normal = "正常血压";
        String normalHigh = "正常高值血压";
        String highPressure = "高血压";
        List<String> axis = Arrays.asList(normal, normalHigh, highPressure);
        DataAnalysisPieVo vo = buildPieVo(answerDtos, axis, normal,
                ((answerDto) -> {
                    String[] split = answerDto.getComment().split("/");
                    double high = Double.parseDouble(split[0]);
                    double low = Double.parseDouble(split[0]);
                    if (high < 120 && low < 80) {
                        return normal;
                    } else if ((high >= 120 && high <= 139) || (low >= 80 && low <= 89)) {
                        return normalHigh;
                    } else {
                        return highPressure;
                    }
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * BMI水平
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("BMILevel")
    public Object BMILevel(@RequestBody DataAnalysisDto dto) {
        // 偏瘦：＜18.5；正常：≥18.5且＜24；偏胖：≥24且＜28；肥胖：≥28
        List<AnswerDto> heightList = answerService.selectSingleAnswerByUser("172", dto);
        List<AnswerDto> weightList = answerService.selectSingleAnswerByUser("173", dto);
        List<AnswerDto> bmiList = new ArrayList<>();
        for (int i = 0; i < Math.min(heightList.size(), weightList.size()); i++) {
            QuestionAnswer heightAnswer = heightList.get(i);
            Optional<AnswerDto> any = weightList.stream().filter(s -> s.getUserId().equals(heightAnswer.getUserId())).findAny();
            if (any.isPresent()) {
                QuestionAnswer weightAnswer = any.get();
                double bmi = userInformationService.getBmi(heightAnswer.getComment(), weightAnswer.getComment());
                AnswerDto e = new AnswerDto();
                e.setOptionValue(bmi + "");
                bmiList.add(e);
            }
        }
        List<String> axis = Arrays.asList("偏瘦", "正常", "偏胖", "肥胖");
        DataAnalysisPieVo vo = buildPieVo(bmiList, axis, "正常",
                ((answerDto) -> {
                    double bmi = Double.parseDouble(answerDto.getOptionValue());
                    if (bmi < 18.5) {
                        return "偏瘦";
                    } else if (bmi >= 18.5 && bmi <= 24) {
                        return "正常";
                    } else if (bmi >= 24 && bmi <= 28) {
                        return "偏胖";
                    } else {
                        return "肥胖";
                    }
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 血糖
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("bloodSugar")
    public Object bloodSugar(@RequestBody DataAnalysisDto dto) {
        List<AnswerDto> bloodSugarList = answerService.selectSingleAnswerByUser("177", dto);
        List<AnswerDto> danbaiList = answerService.selectSingleAnswerByUser("178", dto);
        List<AnswerDto> result = new ArrayList<>();
        List<AnswerDto> forEachList = bloodSugarList.size() < danbaiList.size() ? danbaiList : bloodSugarList;
        for (AnswerDto answerDto : forEachList) {
            AnswerDto e = new AnswerDto();
            bloodSugarList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setOptionValue(value.getComment()));
            danbaiList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setComment(value.getComment()));
            result.add(e);
        }
        List<String> axis = Arrays.asList("血糖正常", "血糖升高");
        DataAnalysisPieVo vo = buildPieVo(result, axis, "血糖正常",
                ((answerDto) -> {
                    // 血糖升高：空腹血糖＞6.1mmol/L或糖化血红蛋白＞6%
                    try {
                        double bloodSugar = Double.parseDouble(answerDto.getOptionValue());
                        if (bloodSugar > 6.1) {
                            return "血糖升高";
                        }
                    } catch (NumberFormatException e) {
                    }
                    try {
                        double danbai = Double.parseDouble(answerDto.getComment());
                        if (danbai > 6) {
                            return "血糖升高";
                        }
                    } catch (NumberFormatException e) {
                    }
                    return "血糖正常";
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 血脂
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("bloodFat")
    public Object bloodFat(@RequestBody DataAnalysisDto dto) {
        // 您的甘油三脂（TG）您的血清低密度脂蛋白胆固醇（LDL-C） 胆固醇（TCHO）
        List<AnswerDto> TCHOList = answerService.selectSingleAnswerByUser("179", dto);
        List<AnswerDto> ganyouList = answerService.selectSingleAnswerByUser("180", dto);
        List<AnswerDto> xueqingList = answerService.selectSingleAnswerByUser("181", dto);
        List<AnswerDto> result = new ArrayList<>();
        List<AnswerDto> forEachList = TCHOList;
        if (forEachList.size() < ganyouList.size()) {
            forEachList = ganyouList;
        }
        if (forEachList.size() < xueqingList.size()) {
            forEachList = xueqingList;
        }
        for (AnswerDto answerDto : forEachList) {
            AnswerDto e = new AnswerDto();
            TCHOList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setOptionValue(value.getComment()));
            ganyouList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setComment(value.getComment()));
            xueqingList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setId(value.getComment()));
            result.add(e);
        }
        // 血脂异常：总胆固醇≥5.2mmol/L或甘油三酯≥1.7mmol/L或低密度脂蛋白胆固醇≥3.4mmol/L
        List<String> axis = Arrays.asList("血脂正常", "血脂异常");
        DataAnalysisPieVo vo = buildPieVo(result, axis, "血脂正常",
                ((answerDto) -> {
                    try {
                        double tcho = Double.parseDouble(answerDto.getOptionValue());
                        if (tcho >= 5.2) {
                            return "血脂异常";
                        }
                    } catch (NumberFormatException e) {
                    }
                    try {
                        double ganyou = Double.parseDouble(answerDto.getComment());
                        if (ganyou >= 1.7) {
                            return "血脂异常";
                        }
                    } catch (NumberFormatException e) {
                    }
                    try {
                        double xueqing = Double.parseDouble(answerDto.getId());
                        if (xueqing >= 3.4) {
                            return "血脂异常";
                        }
                    } catch (NumberFormatException e) {
                    }
                    return "血脂正常";
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 血钙
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("bloodCalcium")
    public Object bloodCalcium(@RequestBody DataAnalysisDto dto) {
        List<AnswerDto> allCaList = answerService.selectSingleAnswerByUser("225", dto);
        List<AnswerDto> caList = answerService.selectSingleAnswerByUser("226", dto);
        List<AnswerDto> result = new ArrayList<>();

        List<AnswerDto> forEachList = allCaList.size() > caList.size() ? allCaList : caList;
        for (AnswerDto answerDto : forEachList) {
            AnswerDto e = new AnswerDto();
            allCaList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setOptionValue(value.getComment()));
            caList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setComment(value.getComment()));
            result.add(e);
        }
        List<String> axis = Arrays.asList("血钙正常", "血钙偏低", "血钙偏高");
        // 血钙正常：血清总钙浓度≥2.25且≤2.75mmol/L或者血清离子钙浓度≥1.10且≤1.37mmol/L；
        // 血钙偏低：血清总钙浓度＜2.25mmol/L或者血清离子钙浓度＜1.10mmol/L；
        // 血钙偏高：血清总钙浓度＞2.75mmol/L或者血清离子钙浓度＞1.37mmol/L
        DataAnalysisPieVo vo = buildPieVo(result, axis, "血钙正常",
                ((answerDto) -> {
                    try {
                        double allCa = Double.parseDouble(answerDto.getOptionValue());
                        if (allCa >= 2.25 && allCa <= 2.75) {
                            return "血钙正常";
                        } else if (allCa < 2.25) {
                            return "血钙偏低";
                        } else {
                            return "血钙偏高";
                        }
                    } catch (NumberFormatException e) {
                    }
                    try {
                        double ca = Double.parseDouble(answerDto.getComment());
                        if (ca >= 1.1 && ca <= 1.37) {
                            return "血钙正常";
                        } else if (ca < 1.10) {
                            return "血钙偏低";
                        } else {
                            return "血钙偏高";
                        }
                    } catch (NumberFormatException e) {
                    }
                    return "血钙正常";
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 血镁
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("bloodMagnesium")
    public Object bloodMagnesium(@RequestBody DataAnalysisDto dto) {
        // 血镁正常：≥0.75且≤1.25 mmol/L；血镁偏低：＜0.75 mmol/L；血镁偏高：＞1.25 mmol/L
        List<AnswerDto> allCaList = answerService.selectSingleAnswerByUser("227", dto);
        List<String> axis = Arrays.asList("血镁正常", "血镁偏低", "血镁偏高");
        DataAnalysisPieVo vo = buildPieVo(allCaList, axis, "血镁正常",
                ((answerDto) -> {
                    double allCa = Double.parseDouble(answerDto.getComment());
                    if (allCa >= 0.75 && allCa <= 1.25) {
                        return "血镁正常";
                    } else if (allCa < 0.75) {
                        return "血镁偏低";
                    } else {
                        return "血镁偏高";
                    }
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 同型半胱氨酸
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("homocysteine")
    public Object homocysteine(@RequestBody DataAnalysisDto dto) {
        // 同型半胱氨酸正常：＜10μmol/L；同型半胱氨酸轻度升高：≥10μmol/L且＜15μmol/L；同型半胱氨酸中度升高：≥15μmol/L且＜30μmol/L；同型半胱氨酸重度升高：≥30μmol/L】
        List<AnswerDto> allCaList = answerService.selectSingleAnswerByUser("228", dto);
        List<String> axis = Arrays.asList("同型半胱氨酸正常", "同型半胱氨酸轻度升高", "同型半胱氨酸中度升高", "同型半胱氨酸重度升高");
        DataAnalysisPieVo vo = buildPieVo(allCaList, axis, "同型半胱氨酸正常",
                ((answerDto) -> {
                    double allCa = Double.parseDouble(answerDto.getComment());
                    if (allCa < 10) {
                        return "同型半胱氨酸正常";
                    } else if (allCa >= 10 && allCa < 15) {
                        return "同型半胱氨酸轻度升高";
                    } else if (allCa >= 15 && allCa < 30) {
                        return "同型半胱氨酸中度升高";
                    } else {
                        return "同型半胱氨酸重度升高";
                    }
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 坐姿
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("sittingPosture")
    public Object sittingPosture(@RequestBody DataAnalysisDto dto) {
        // > 6 久坐 <6 正常
        List<AnswerDto> allCaList = answerService.selectSingleAnswerByUser("62", dto);
        List<String> axis = Arrays.asList("正常", "久坐");
        DataAnalysisPieVo vo = buildPieVo(allCaList, axis, "正常",
                ((answerDto) -> {
                    double allCa = Double.parseDouble(answerDto.getComment());
                    if (allCa < 6 * 60) {
                        return "正常";
                    } else {
                        return "久坐";
                    }
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 睡眠
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("sleep")
    public Object sleep(@RequestBody DataAnalysisDto dto) {
        // 工作睡眠 < 6 或 161选择 3 4 睡眠不足
        List<AnswerDto> sleepList = answerService.selectSingleAnswerByUser("64", dto);
        List<AnswerDto> optionList = answerService.selectSingleAnswerByUser("161", dto);
        List<AnswerDto> forEachList = sleepList.size() > optionList.size() ? sleepList : optionList;
        List<AnswerDto> result = new ArrayList<>();
        for (AnswerDto answerDto : forEachList) {
            AnswerDto e = new AnswerDto();
            sleepList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setComment(value.getComment()));
            optionList.stream().filter(s -> s.getUserId().equals(answerDto.getUserId()))
                    .findAny()
                    .ifPresent(value -> e.setOptionId(value.getOptionId()));
            result.add(e);
        }
        List<String> axis = Arrays.asList("正常", "睡眠不足");
        DataAnalysisPieVo vo = buildPieVo(result, axis, "正常",
                ((answerDto) -> {
                    double sleep = Double.parseDouble(answerDto.getComment());
                    String option = answerDto.getOptionId();
                    if (sleep < 6 * 60 || "396".equals(option) || "397".equals(option)) {
                        return "睡眠不足";
                    } else {
                        return "正常";
                    }
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 年龄
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("age")
    public Object age(@RequestBody DataAnalysisDto dto) {
        // 大于等于18小于45，大于等于45小于60，大于等于60
        UserInformationDto userInformationDto = new UserInformationDto();
        userInformationDto.setQueryStartDateStart(dto.getStartDate());
        userInformationDto.setQueryStartDateEnd(dto.getEndDate());
        List<UserInformationDto> allList = userInformationService.findAllList(userInformationDto);
        List<String> axis = Arrays.asList("0-18", "18-45", "45-60", "60-");
        List<AnswerDto> list = new ArrayList<>();
        for (UserInformationDto informationDto : allList) {
            AnswerDto e = new AnswerDto();
            e.setComment(informationDto.getAge());
            list.add(e);
        }
        DataAnalysisPieVo vo = buildPieVo(list, axis, "0-18",
                ((answerDto) -> {
                    double age = Double.parseDouble(answerDto.getComment());
                    if (age <= 18) {
                        return "0-18";
                    } else if (age > 18 && age < 45) {
                        return "18-45";
                    } else if (age >= 45 && age < 60) {
                        return "45-60";
                    } else {
                        return "60-";
                    }
                }));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 职业
     *
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("occupation")
    public Object occupation(@RequestBody DataAnalysisDto dto) {
        UserInformationDto userInformationDto = new UserInformationDto();
        userInformationDto.setQueryStartDateStart(dto.getStartDate());
        userInformationDto.setQueryStartDateEnd(dto.getEndDate());
        List<UserInformationDto> allList = userInformationService.findAllList(userInformationDto);
        List<String> axis = dictService.dictList("occupation").stream()
                .map(SysDictDto::getDescription)
                .collect(Collectors.toList());
        List<AnswerDto> list = new ArrayList<>();
        for (UserInformationDto informationDto : allList) {
            AnswerDto e = new AnswerDto();
            e.setComment(informationDto.getOccupationDto());
            list.add(e);
        }
        DataAnalysisPieVo vo = buildPieVo(list, axis, "无职业", (QuestionAnswer::getComment));
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 体力
     */
    @ResponseBody
    @RequestMapping("physicalStrength")
    public Object physicalStrength(@RequestBody DataAnalysisDto dto) {
        PsychologyResult result = new PsychologyResult();
        result.setQueryStartCreateDate(dto.getStartDate());
        result.setQueryEndCreateDate(dto.getEndDate());
        List<PsychologyResult> psychologyResults = psychologyResultService.listDistinctUserByEntity(result);
        List<String> axis = Arrays.asList("高体力活动", "中体力活动", "低体力活动");
        List<AnswerDto> list = new ArrayList<>();
        for (PsychologyResult psychologyResult : psychologyResults) {
            String physical = psychologyResult.getPhysical();
            if (StrUtil.isNotBlank(physical)) {
                try {
                    AnswerDto e = new AnswerDto();
                    JSONObject jsonObject = JSONUtil.parseObj(physical);
                    String value = jsonObject.getStr("体力活动情况");
                    e.setComment(value);
                    list.add(e);
                } catch (Exception ignored) {
                }
            }
        }
        DataAnalysisPieVo vo = buildPieVo(list, axis, "低体力活动", (QuestionAnswer::getComment));
        return ResultUtils.returnSuccess(vo);
    }
}
