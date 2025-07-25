package com.life.decision.support.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.*;
import com.life.decision.support.enums.ModuleType;
import com.life.decision.support.http.PyHttp;
import com.life.decision.support.http.PyKey;
import com.life.decision.support.mapper.QuestionInformationMapper;
import com.life.decision.support.pojo.*;
import com.life.decision.support.result.QuestionInformationResultDto;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionnaireGroupInformationService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.service.impl.*;
import com.life.decision.support.utils.ResultUtils;
import com.life.decision.support.vo.ChineseMedicineVo;
import com.life.decision.support.vo.PsychologyResultVo;
import com.life.decision.support.vo.RecipeResultVo;
import com.life.decision.support.vo.SportResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/result")
public class QuestionnaireResultsController {
    @Autowired
    private PyHttp pyHttp;
    @Autowired
    IQuestionnaireGroupInformationService groupInformationService;
    @Autowired
    IQuestionnaireSubmitInformationService submitInformationService;
    @Autowired
    private QuestionInformationMapper informationMapper;
    @Autowired
    private IQuestionAnswerService questionAnswerService;
    @Autowired
    private SportsResultServiceImpl sportsResultService;
    @Autowired
    private PsychologyResultServiceImpl psychologyResultService;
    @Autowired
    private RecipeResultServiceImpl recipeResultService;
    @Autowired
    private ChineseMedicineServiceImpl chineseMedicineService;
    @Autowired
    private ModuleAccessDetailsService moduleAccessDetailsService;


    @RequestMapping("changeRecipe")
    public Map<String, Object> changeRecipe(@RequestBody QueryDto dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        if (StrUtil.isBlank(dto.getDate())) {
            dto.setDate(LocalDate.now().toString());
        }
        QuestionnaireGroupInformation groupEntity = groupInformationService.getByUserIdHasSuccess(userId);
        if (groupEntity != null) {
            groupEntity.setUserId(userId);
            JSONObject pyResult = pyHttp.getPyResult(groupEntity);
            JSONArray jsonArray = pyResult.getJSONArray(PyKey.DIET.getKey());
            int dayOfYear = LocalDate.now().getDayOfYear();
            JSONObject recipeObj = jsonArray.get(dayOfYear % jsonArray.size(), JSONObject.class)
                    .getJSONObject(PyKey.RECIPE.getKey());
            // 更新这三个数据
            RecipeResultDto resultDto = new RecipeResultDto();
            resultDto.setUserId(userId);
            resultDto.setQueryDate(dto.getDate());
            RecipeResult recipeResult = recipeResultService.findByEntity(resultDto);
            if (recipeResult == null) {
                return ResultUtils.returnError("更换食谱结果失败，未找到当天食谱");
            }

            recipeResult.setBreakfast(recipeObj.getStr(PyKey.BREAKFAST.getKey()));
            recipeResult.setLunch(recipeObj.getStr(PyKey.LUNCH.getKey()));
            recipeResult.setDinner(recipeObj.getStr(PyKey.DINNER.getKey()));
            recipeResultService.saveOrUpdate(recipeResult);

            RecipeResultVo vo = new RecipeResultVo();
            recipeResultService.loadRecipeVo(dto, recipeResult, vo);
            return ResultUtils.returnSuccess(vo);
        }
        return ResultUtils.returnError("未找到已完成的完整问卷");
    }


    @RequestMapping("recipeDateRecord")
    public Map<String, Object> recipeDateRecord(@RequestBody QueryDto dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        return ResultUtils.returnSuccess(recipeResultService.getRecipeDateRecord(dto));
    }

    /**
     * 食谱
     */
    @RequestMapping("recipe")
    public Map<String, Object> recipe(@RequestBody QueryDto dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        RecipeResultVo vo = recipeResultService.getResultVo(dto);
        moduleAccessDetailsService.saveApi(ModuleType.RECIPE,"result/recipe",userId);
        return ResultUtils.returnSuccess(vo);
    }


    @RequestMapping("changeSports")
    public Map<String, Object> changeSports(@RequestBody QueryDto dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        if (StrUtil.isBlank(dto.getDate())) {
            dto.setDate(LocalDate.now().toString());
        }
        QuestionnaireGroupInformation groupEntity = groupInformationService.getByUserIdHasSuccess(userId);
        if (groupEntity != null) {
            groupEntity.setUserId(userId);
            JSONObject pyResult = pyHttp.getPyResult(groupEntity);
            JSONArray jsonArray = pyResult.getJSONArray(PyKey.SPORTS.getKey());
            int dayOfYear = LocalDate.now().getDayOfYear();
            List<Object> filterArray = jsonArray.stream()
                    .filter(obj -> {
                        JSONArray sport = ((JSONObject) obj).getJSONArray("具体的运动");
                        if (sport != null) {
                            return sport.size() > 0;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
            JSONObject sportObj = (JSONObject) filterArray.get(dayOfYear % filterArray.size());
            // 更新这三个数据
            SportsResultDto resultDto = new SportsResultDto();
            resultDto.setUserId(dto.getUserId());
            resultDto.setQueryDate(dto.getDate());
            SportsResult byEntity = sportsResultService.findByEntity(resultDto);
            if (byEntity == null) {
                return ResultUtils.returnError("更新运动结果失败，未找到当天运动");
            }
            byEntity.setWarmUpBeforeExercise(sportObj.getStr("运动前热身"));
            byEntity.setSpecificSports(sportObj.getStr("具体的运动"));
            byEntity.setStretchingAfterExercise(sportObj.getStr("运动后拉伸"));
            byEntity.setHealthEducation(sportObj.getStr("健康教育"));
            sportsResultService.saveOrUpdate(byEntity);

            SportResultVo vo = sportsResultService.getVo(dto);
            return ResultUtils.returnSuccess(vo);
        }
        return ResultUtils.returnError("未找到已完成的完整问卷");
    }

    /**
     * 元气运动
     *
     * @param dto
     * @return
     */
    @RequestMapping("sports")
    public Map<String, Object> sports(@RequestBody QueryDto dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        // 获取当天的数据
        SportResultVo sportResultVo = sportsResultService.getVo(dto);
        moduleAccessDetailsService.saveApi(ModuleType.SPORTS,"result/sports",userId);
        return ResultUtils.returnSuccess(sportResultVo);
    }


    @RequestMapping("sportsDateRecord")
    public Map<String, Object> sportsDateRecord(@RequestBody QueryDto dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        return ResultUtils.returnSuccess(sportsResultService.getSportsDateRecord(dto));
    }


    /**
     * 心理
     *
     * @param dto
     * @return
     */
    @RequestMapping("psychology")
    public Map<String, Object> psychology(@RequestBody PsychologyResult dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        PsychologyResultVo vo = psychologyResultService.getVo(dto);
        moduleAccessDetailsService.saveApi(ModuleType.PSYCHOLOGY,"result/psychology",userId);
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 中医
     */
    @RequestMapping("medicine")
    public Map<String, Object> medicine(@RequestBody ChineseMedicine dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        ChineseMedicineVo vo = chineseMedicineService.getVo(dto);
        moduleAccessDetailsService.saveApi(ModuleType.MEDICINE,"result/medicine",userId);
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 查看问卷组问题和答案
     *
     * @param user
     * @return
     */
    @RequestMapping("getResult")
    public JSONObject getResult(@RequestBody UserInformationDto user) {
        String id = StrUtil.isBlank(user.getId()) ? user.getUserId() : user.getId();
        List<QuestionnaireSubmitInformation> submitList = submitInformationService.listLatestSubmittedQuestionnaire(id);
        return getQuestionnaireGroupAnswer(id, submitList);
    }

    private JSONObject getQuestionnaireGroupAnswer(String userId, List<QuestionnaireSubmitInformation> submitList) {
        JSONObject result = new JSONObject();
        result.putOpt("userId", userId);
        JSONObject questionnaire = new JSONObject();
        for (QuestionnaireSubmitInformation info : submitList) {
            List<QuestionInformationDto> list = informationMapper.listById(info.getQuestionnaireId());
            QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.setUserId(userId);
            questionAnswer.setQuestionnaireId(info.getQuestionnaireId());
            questionAnswer.setSubmitId(info.getId());
            List<QuestionAnswer> answerList = questionAnswerService.findList(questionAnswer);
            JSONArray question = new JSONArray();
            for (QuestionInformationDto questionInformationDto : list) {
                uploadAnswer(answerList, questionInformationDto);
                question.add(BeanUtil.copyProperties(questionInformationDto, QuestionInformationResultDto.class));
                if (CollUtil.isNotEmpty(questionInformationDto.getChild())) {
                    for (QuestionInformationDto informationDto : questionInformationDto.getChild()) {
                        uploadAnswer(answerList, informationDto);
                        question.add(BeanUtil.copyProperties(informationDto, QuestionInformationResultDto.class));
                    }
                }
            }
            questionnaire.putOpt(info.getQuestionnaireId(), question);
        }
        result.putOpt("questionnaire", questionnaire);
        return result;
    }

    @RequestMapping("getResultByGroupId")
    public JSONObject getResultByGroupId(@RequestBody QuestionnaireGroupInformation groupInformation) {
        List<QuestionnaireSubmitInformation> submitList = submitInformationService.listByGroupId(groupInformation.getGroupId());
        return getQuestionnaireGroupAnswer(groupInformation.getUserId(), submitList);
    }

    private void uploadAnswer(List<QuestionAnswer> answerList, QuestionInformationDto dto) {
        Optional<QuestionAnswer> any = answerList.stream()
                .filter(s -> s.getQuestionId().equals(dto.getId()))
                .findAny();
        dto.setQuestionAnswer(any.orElse(new QuestionAnswer()));
    }
}
