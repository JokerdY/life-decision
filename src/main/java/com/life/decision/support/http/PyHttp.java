package com.life.decision.support.http;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.common.URL;
import com.life.decision.support.controller.QuestionnaireResultsController;
import com.life.decision.support.pojo.PsychologyResult;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import com.life.decision.support.pojo.RecipeResult;
import com.life.decision.support.pojo.SportsResult;
import com.life.decision.support.service.impl.PsychologyResultServiceImpl;
import com.life.decision.support.service.impl.RecipeResultServiceImpl;
import com.life.decision.support.service.impl.SportsResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

@Component
public class PyHttp {
    @Autowired
    private QuestionnaireResultsController resultsController;
    @Autowired
    private RecipeResultServiceImpl recipeResultService;
    @Autowired
    private PsychologyResultServiceImpl psychologyResultService;
    @Autowired
    private SportsResultServiceImpl sportsResultService;
    private static final List<String> removeList = Arrays.asList("215", "216", "217", "218", "219",
            "220", "221", "222", "223", "224");


    @Transactional
    public void parsePyResult(QuestionnaireGroupInformation entity) {
        String userId = entity.getUserId();

        JSONObject pyResult = getPyResult(entity);
        // 存储结果
        JSONObject mental = pyResult.getJSONObject("Mental");
        PsychologyResult psychologyResult = new PsychologyResult();
        psychologyResult.setResult(mental.getStr("判断结果"));
        psychologyResult.setAdvice(mental.getStr("建议"));
        psychologyResult.setUserId(userId);
        psychologyResult.setHealthEducation(mental.getStr("健康教育"));
        psychologyResult.setId(IdUtil.fastSimpleUUID());
        psychologyResultService.save(psychologyResult);

        // 根据最新的日期 更新食谱和运动
        saveThreeMonths(pyResult, PyKey.SPORTS.getKey(), PyKey.DIET.getKey(), entity,
                (obj, date) -> {
                    SportsResult sportsResult = new SportsResult();
                    if (obj == null || obj.isEmpty()) {
                        sportsResult.setWarmUpBeforeExercise(null);
                        sportsResult.setSpecificSports(null);
                        sportsResult.setStretchingAfterExercise(null);
                        sportsResult.setHealthEducation(null);
                    }else {
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
        JSONArray jsonArray = questionnaire.getJSONArray("4");
        jsonArray.removeIf(obj -> removeList.contains(((JSONObject) obj).getStr("id")));
        return JSONUtil.parseObj(HttpUtil.post(URL.PY_URL.getUrl(), resultByGroupId.toString()));
//        return JSONUtil.parseObj(FileUtil.readString(new File("C:\\Users\\hspcadmin\\Documents\\WeChat Files\\wxid_1683106826411\\FileStorage\\File\\2022-08\\3.test_back_new(1).json"), Charset.defaultCharset()));
    }
}
