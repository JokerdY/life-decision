package com.life.decision.support.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.bo.UrlAdvice;
import com.life.decision.support.dto.*;
import com.life.decision.support.http.PyHttp;
import com.life.decision.support.http.PyKey;
import com.life.decision.support.mapper.QuestionInformationMapper;
import com.life.decision.support.pojo.*;
import com.life.decision.support.result.QuestionInformationResultDto;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionnaireGroupInformationService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.service.impl.PsychologyResultServiceImpl;
import com.life.decision.support.service.impl.RecipeResultServiceImpl;
import com.life.decision.support.service.impl.SportsResultServiceImpl;
import com.life.decision.support.utils.ResultUtils;
import com.life.decision.support.vo.PsychologyResultVo;
import com.life.decision.support.vo.RecipeResultVo;
import com.life.decision.support.vo.SportResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/result")
public class QuestionnaireResultsController {
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
    private PyHttp pyHttp;

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
            loadRecipeVo(recipeResult, vo);
            return ResultUtils.returnSuccess(vo);
        }
        return ResultUtils.returnError("未找到已完成的完整问卷");
    }

    private void loadRecipeVo(RecipeResult recipeResult, RecipeResultVo vo) {
        vo.setTotalCaloriesEntity(recipeResultService.getTotalCaloriesEntity(recipeResult));
        vo.setHealthEducation(getHealthEducation(recipeResult.getHealthEducation()));
        vo.setBreakfastRecipe(recipeResultService.getRecipeEntity(recipeResult.getBreakfast()));
        vo.setLunchRecipe(recipeResultService.getRecipeEntity(recipeResult.getLunch()));
        vo.setDinnerRecipe(recipeResultService.getRecipeEntity(recipeResult.getDinner()));
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
        if (StrUtil.isBlank(dto.getDate())) {
            dto.setDate(LocalDate.now().toString());
        }
        String yearAndMouth = LocalDate.parse(dto.getDate()).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        // 获取当月的所有记录
        List<String> dateRecord = recipeResultService.listByYearAndMouth(yearAndMouth, userId);
        // 获取当天的数据
        RecipeResultDto resultDto = new RecipeResultDto();
        resultDto.setUserId(dto.getUserId());
        resultDto.setQueryDate(dto.getDate());
        RecipeResult byEntity = recipeResultService.findByEntity(resultDto);
        RecipeResultVo vo = new RecipeResultVo();
        vo.setDateRecord(dateRecord);
        if (byEntity != null) {
            loadRecipeVo(byEntity, vo);
        }
        return ResultUtils.returnSuccess(vo);
    }

    public List<ContentAdvice> getHealthEducation(String byEntity) {
        return JSONUtil.parseArray(byEntity).stream()
                .map((obj) -> {
                    JSONObject temp = (JSONObject) obj;
                    Set<String> keySet = temp.keySet();
                    if (keySet.size() > 0) {
                        String[] key = keySet.toArray(new String[2]);
                        return new ContentAdvice(key[0], temp.getStr(key[0]));
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
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
        if (StrUtil.isBlank(dto.getDate())) {
            dto.setDate(LocalDate.now().toString());
        }
        String yearAndMouth = LocalDate.parse(dto.getDate()).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        // 获取当月的所有记录
        List<String> dateRecord = sportsResultService.listByYearAndMouth(yearAndMouth, userId);
        // 获取当天的数据
        SportsResultDto resultDto = new SportsResultDto();
        resultDto.setUserId(dto.getUserId());
        resultDto.setQueryDate(dto.getDate());
        SportsResult byEntity = sportsResultService.findByEntity(resultDto);
        SportResultVo sportResultVo = new SportResultVo();
        sportResultVo.setDateRecord(dateRecord);
        if (byEntity != null) {
            List<UrlAdvice> beforeList = sportsResultService.getAdviceListTempLate(byEntity.getWarmUpBeforeExercise(), "推荐热身运动信息", "推荐热身运动持续时间");
            sportResultVo.setBeforeSports(beforeList);
            List<UrlAdvice> specialList = sportsResultService.getAdviceListTempLate(byEntity.getSpecificSports(), "推荐具体运动信息", "推荐具体运动持续时间");
            sportResultVo.setSpecialSports(specialList);
            List<UrlAdvice> afterList = sportsResultService.getAdviceListTempLate(byEntity.getStretchingAfterExercise(), "运动后拉伸信息", "运动后拉伸持续时间");
            sportResultVo.setAfterSports(afterList);
            sportResultVo.setHealthEducation(getHealthEducation(byEntity.getHealthEducation()));
        }
        return ResultUtils.returnSuccess(sportResultVo);
    }

    @RequestMapping("psychology")
    public Map<String, Object> psychology(@RequestBody PsychologyResult dto) {
        String userId = dto.getUserId();
        if (StrUtil.isBlank(userId)) {
            return ResultUtils.returnError("用户id缺失");
        }
        PsychologyResultVo vo = psychologyResultService.getVo(dto);
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
        JSONObject result = getQuestionnaireGroupAnswer(groupInformation.getUserId(), submitList);
        return result;
    }

    private void uploadAnswer(List<QuestionAnswer> answerList, QuestionInformationDto dto) {
        answerList.stream()
                .filter(s -> s.getQuestionId().equals(dto.getId()))
                .findAny()
                .ifPresent(dto::setQuestionAnswer);
    }
}
