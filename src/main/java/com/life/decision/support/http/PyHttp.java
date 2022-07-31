package com.life.decision.support.http;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.controller.QuestionnaireResultsController;
import com.life.decision.support.pojo.PsychologyResult;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import com.life.decision.support.pojo.SportsResult;
import com.life.decision.support.service.impl.PsychologyResultServiceImpl;
import com.life.decision.support.service.impl.RecipeResultServiceImpl;
import com.life.decision.support.service.impl.SportsResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.charset.Charset;
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
        saveThreeMonths(pyResult, "Sports", "food", entity,
                (obj, date) -> {
                    SportsResult sportsResult = new SportsResult();
                    sportsResult.setWarmUpBeforeExercise(obj.getStr("运动前热身"));
                    sportsResult.setSpecificSports(obj.getStr("具体的运动"));
                    sportsResult.setStretchingAfterExercise(obj.getStr("运动后拉伸"));
                    sportsResult.setHealthEducation(obj.getStr("健康教育"));
                    sportsResult.setRDate(date);
                    sportsResult.setUserId(userId);
                    sportsResult.setId(IdUtil.fastSimpleUUID());
                    sportsResultService.saveOrUpdate(sportsResult);
                }, (obj, date) -> {

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

    private JSONObject getPyResult(QuestionnaireGroupInformation entity) {
        JSONObject resultByGroupId = resultsController.getResultByGroupId(entity);
        JSONObject questionnaire = resultByGroupId.getJSONObject("questionnaire");
        JSONArray jsonArray = questionnaire.getJSONArray("4");
        jsonArray.removeIf(obj -> removeList.contains(((JSONObject) obj).getStr("id")));
//      return JSONUtil.parseObj(HttpUtil.post(URL.PY_URL.getUrl(), resultByGroupId.toString()));
        return JSONUtil.parseObj(FileUtil.readString(new File("C:\\Users\\hspcadmin\\Documents\\WeChat Files\\wxid_1683106826411\\FileStorage\\File\\2022-07\\3.test_back.json"), Charset.defaultCharset()));
    }
}
