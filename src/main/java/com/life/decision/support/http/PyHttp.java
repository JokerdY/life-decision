package com.life.decision.support.http;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.common.URL;
import com.life.decision.support.controller.QuestionnaireResultsController;
import com.life.decision.support.dto.UserInHomeVo;
import com.life.decision.support.pojo.*;
import com.life.decision.support.service.PsychologicalOutcomeService;
import com.life.decision.support.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

@Component
@Slf4j
public class PyHttp {
    @Autowired
    private QuestionnaireResultsController resultsController;
    @Autowired
    private RecipeResultServiceImpl recipeResultService;
    @Autowired
    private PsychologyResultServiceImpl psychologyResultService;
    @Autowired
    private SportsResultServiceImpl sportsResultService;
    @Autowired
    private ChineseMedicineServiceImpl chineseMedicineService;
    @Autowired
    private PsychologicalOutcomeService psychologicalOutcomeService;
    @Autowired
    private UserInformationServiceImpl userInformationService;

    private static final List<String> removeList = Arrays.asList("215", "216", "217", "218", "219",
            "220", "221", "222", "223", "224");


    @Transactional
    public void parsePyResult(QuestionnaireGroupInformation entity) {
        String userId = entity.getUserId();

        JSONObject pyResult = getPyResult(entity);
        // 存储结果
        JSONObject mental = pyResult.getJSONObject("Mental");
        PsychologyResult psychologyResult = new PsychologyResult();
        JSONObject score = mental.getJSONObject("得分");
        PsychologicalOutcome psychologicalOutcome = new PsychologicalOutcome();
        psychologicalOutcome.setAnxiety(score.getStr("A"));
        psychologicalOutcome.setDepression(score.getStr("D"));
        psychologicalOutcome.setPressure(score.getStr("P"));
        psychologicalOutcome.setUserId(entity.getUserId());
        psychologicalOutcomeService.save(psychologicalOutcome);

        psychologyResult.setResult(mental.getStr("判断结果"));
        psychologyResult.setAdvice(mental.getStr("建议"));
        psychologyResult.setUserId(userId);
        psychologyResult.setHealthEducation(mental.getStr("健康教育"));
        psychologyResult.setId(IdUtil.fastSimpleUUID());
        psychologyResult.setGroupId(entity.getGroupId());
        psychologyResultService.save(psychologyResult);
        JSONObject tcm = pyResult.getJSONObject(PyKey.TCM.getKey());
        ChineseMedicine chineseMedicine = new ChineseMedicine();
        chineseMedicine.setAcupressure(tcm.getStr("穴位按摩"));
        chineseMedicine.setFiveElementsMusic(tcm.getStr("五行音乐"));
        chineseMedicine.setUserId(userId);
        chineseMedicine.setId(IdUtil.fastSimpleUUID());
        chineseMedicine.setGroupId(entity.getGroupId());
        chineseMedicineService.save(chineseMedicine);

        // 根据最新的日期 更新食谱和运动
        saveThreeMonths(pyResult, PyKey.SPORTS.getKey(), PyKey.DIET.getKey(), entity,
                (obj, date) -> {
                    SportsResult sportsResult = new SportsResult();
                    if (obj != null && !obj.isEmpty()) {
                        sportsResult.setWarmUpBeforeExercise(obj.getStr("运动前热身"));
                        sportsResult.setSpecificSports(obj.getStr("具体的运动"));
                        sportsResult.setStretchingAfterExercise(obj.getStr("运动后拉伸"));
                        sportsResult.setHealthEducation(obj.getStr("健康教育"));
                    }
                    sportsResult.setRDate(date);
                    sportsResult.setUserId(userId);
                    sportsResult.setId(IdUtil.fastSimpleUUID());
                    sportsResultService.saveOrUpdate(sportsResult);
                }, (obj, date) -> {
                    RecipeResult recipeResult = new RecipeResult();
                    recipeResult.setHealthEducation(obj.getStr("健康教育"));
                    recipeResult.setUserId(userId);
                    recipeResult.setRDate(date);
                    JSONObject recipe = obj.getJSONObject(PyKey.RECIPE.getKey());
                    recipeResult.setBreakfast(recipe.getStr(PyKey.BREAKFAST.getKey()));
                    recipeResult.setLunch(recipe.getStr(PyKey.LUNCH.getKey()));
                    recipeResult.setDinner(recipe.getStr(PyKey.DINNER.getKey()));
                    recipeResult.setTotalCalories(obj.getStr("总热量"));
                    recipeResult.setDietaryAdvice(obj.getStr("膳食建议"));
                    recipeResult.setId(IdUtil.fastSimpleUUID());
                    recipeResultService.saveOrUpdate(recipeResult);
                });
    }

    private void saveThreeMonths(JSONObject pyResult, String sportKey, String recipeKey,
                                 QuestionnaireGroupInformation entity,
                                 BiConsumer<JSONObject, LocalDate> sportConsumer,
                                 BiConsumer<JSONObject, LocalDate> RecipeConsumer) {
        JSONArray sports = pyResult.getJSONArray(sportKey);
        JSONArray recipes = pyResult.getJSONArray(recipeKey);
        LocalDate now = LocalDate.now();
        int size = 0;
        for (int i = 0; i < 90; i++) {
            if (size >= sports.size() || size >= recipes.size()) {
                size = 0;
                JSONObject tempResult = getPyResult(entity);
                sports = tempResult.getJSONArray(sportKey);
                recipes = tempResult.getJSONArray(recipeKey);
            }
            sportConsumer.accept((JSONObject) sports.get(size), now.plusDays(i));
            RecipeConsumer.accept((JSONObject) recipes.get(size), now.plusDays(i));
            size++;
        }
    }

    public JSONObject getPyResult(QuestionnaireGroupInformation entity) {
        JSONObject resultByGroupId = resultsController.getResultByGroupId(entity);
        JSONObject questionnaire = resultByGroupId.getJSONObject("questionnaire");
        UserInformation user = new UserInformation();
        user.setId(entity.getUserId());
        UserInHomeVo userMsg = userInformationService.getUserMsg(user);
        JSONObject userObj = new JSONObject();
        userObj.putOpt("性别", userMsg.getSexDto());
        userObj.putOpt("年龄", userMsg.getAge());
        questionnaire.putOpt("user", userObj);
        // 删除不需要传输的问题
        JSONArray jsonArray = questionnaire.getJSONArray("4");
        jsonArray.removeIf(obj -> removeList.contains(((JSONObject) obj).getStr("id")));
        String result = HttpUtil.post(URL.PY_URL.getUrl(), resultByGroupId.toString());
        log.info("pyResult:{}", result);
        log.info("resultByGroupId:{}", resultByGroupId.toString());
        return JSONUtil.parseObj(result);
    }
}
