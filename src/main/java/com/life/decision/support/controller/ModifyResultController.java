package com.life.decision.support.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.life.decision.support.bo.*;
import com.life.decision.support.dto.ModifyQueryDto;
import com.life.decision.support.dto.QueryDto;
import com.life.decision.support.dto.manager.*;
import com.life.decision.support.pojo.*;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.service.impl.ChineseMedicineServiceImpl;
import com.life.decision.support.service.impl.PsychologyResultServiceImpl;
import com.life.decision.support.service.impl.RecipeResultServiceImpl;
import com.life.decision.support.service.impl.SportsResultServiceImpl;
import com.life.decision.support.utils.ResultUtils;
import com.life.decision.support.vo.ChineseMedicineVo;
import com.life.decision.support.vo.PsychologyResultVo;
import com.life.decision.support.vo.RecipeResultVo;
import com.life.decision.support.vo.SportResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 健康管理界面
 */
@RestController
@RequestMapping("/modify/result")
@Slf4j
public class ModifyResultController {
    @Autowired
    private IQuestionnaireSubmitInformationService submitInformationService;
    @Autowired
    private SportsResultServiceImpl sportsResultService;
    @Autowired
    private PsychologyResultServiceImpl psychologyResultService;
    @Autowired
    private RecipeResultServiceImpl recipeResultService;
    @Autowired
    private ChineseMedicineServiceImpl chineseMedicineService;


    /**
     * 根据submitId查询当前问卷的updateDate
     * 根据updateDate查询当天结果
     */
    @RequestMapping("recipe")
    public Map<String, Object> recipe(@RequestBody ModifyQueryDto dto) {
        if (StrUtil.isBlank(dto.getSubmitId())) {
            return ResultUtils.returnError("缺少submitId");
        }
        QuestionnaireSubmitInformation byId = submitInformationService.getById(dto.getSubmitId());
        QueryDto queryDto = new QueryDto();
        queryDto.setUserId(byId.getUserId());
        if (StrUtil.isNotBlank(dto.getQueryDate())) {
            queryDto.setDate(dto.getQueryDate());
        } else {
            queryDto.setDate(byId.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        RecipeResultVo vo = recipeResultService.getResultVo(queryDto);
        ManagerRecipe result = new ManagerRecipe();
        BeanUtil.copyProperties(vo, result);

        List<ManagerFood> list = new ArrayList<>();
        for (FoodEntity foodEntity : vo.getBreakfastRecipe().getFoodEntities()) {
            loadFood(foodEntity, "早餐", list);
        }
        for (FoodEntity foodEntity : vo.getLunchRecipe().getFoodEntities()) {
            loadFood(foodEntity, "午餐", list);
        }
        for (FoodEntity foodEntity : vo.getDinnerRecipe().getFoodEntities()) {
            loadFood(foodEntity, "晚餐", list);
        }
        result.setManagerFoods(list);
        ManagerElement managerElement = new ManagerElement();
        for (TotalCaloriesEntity.ElementEntity elementEntity : vo.getTotalCaloriesEntity().getEntityList()) {
            if ("碳水化合物".equals(elementEntity.getElement())) {
                managerElement.setTanshuiProportion(elementEntity.getProportion());
                managerElement.setTanshuiWeight(elementEntity.getWeight());
            } else if ("蛋白质".equals(elementEntity.getElement())) {
                managerElement.setDanbaizhiProportion(elementEntity.getProportion());
                managerElement.setDanbaizhiWeight(elementEntity.getWeight());
            } else {
                managerElement.setZhifangProportion(elementEntity.getProportion());
                managerElement.setZhifangWeight(elementEntity.getWeight());
            }
        }
        result.setManagerElement(managerElement);
        result.setBreakfastRecipe(null);
        result.setLunchRecipe(null);
        result.setDinnerRecipe(null);
        result.setTotalCaloriesEntity(null);

        return ResultUtils.returnSuccess(result);
    }

    private void loadFood(FoodEntity foodEntity, String canci, List<ManagerFood> list) {
        ManagerFood temp = new ManagerFood();
        BeanUtil.copyProperties(foodEntity, temp);
        temp.setCanci(canci);
        list.add(temp);
    }

    /**
     * 根据submitId查询当前问卷的updateDate
     * 根据updateDate查询当天结果
     */
    @RequestMapping("updateRecipe")
    public Map<String, Object> updateRecipe(@RequestBody ManagerRecipe vo) {
        try {
            RecipeResult recipeResult = new RecipeResult();
            recipeResult.setId(vo.getId());
            recipeResult.setRDate(LocalDate.parse(vo.getNowDay(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            recipeResult.setUserId(vo.getUserId());

            JSONArray breakfast = getFood("早餐", vo.getManagerFoods());
            recipeResult.setBreakfast(breakfast.toString());

            JSONArray lunch = getFood("午餐", vo.getManagerFoods());
            recipeResult.setLunch(lunch.toString());

            JSONArray dinner = getFood("晚餐", vo.getManagerFoods());
            recipeResult.setDinner(dinner.toString());

            JSONObject calories = new JSONObject();
            JSONObject tan = new JSONObject();
            ManagerElement managerElement = vo.getManagerElement();
            tan.putOpt("重量", managerElement.getTanshuiWeight());
            tan.putOpt("占比", managerElement.getTanshuiProportion());
            calories.putOpt("碳水化合物", tan);

            JSONObject zhi = new JSONObject();
            zhi.putOpt("重量", managerElement.getZhifangWeight());
            zhi.putOpt("占比", managerElement.getZhifangProportion());
            calories.putOpt("脂肪", zhi);

            JSONObject dan = new JSONObject();
            dan.putOpt("重量", managerElement.getDanbaizhiWeight());
            dan.putOpt("占比", managerElement.getDanbaizhiProportion());
            calories.putOpt("蛋白质", dan);
            recipeResult.setTotalCalories(calories.toString());
            JSONArray health = new JSONArray();
            for (ContentAdvice contentAdvice : vo.getHealthEducation()) {
                JSONObject temp = new JSONObject();
                temp.putOpt(contentAdvice.getName(), contentAdvice.getContent());
                health.put(temp);
            }
            recipeResult.setHealthEducation(health.toString());
            recipeResult.setDietaryAdvice(new JSONArray(vo.getDietaryAdvice()).toString());
            recipeResultService.saveOrUpdateById(recipeResult);
        } catch (Exception e) {
            log.error("",e);
            return ResultUtils.returnError(e.getMessage() + "->funcName:updateRecipe");
        }
        return ResultUtils.returnSuccess("提交成功");
    }

    private JSONArray getFood(String type, List<ManagerFood> list) {
        JSONArray food = new JSONArray();
        list.stream().filter(s -> type.equals(s.getCanci())).collect(Collectors.toList())
                .forEach(entity -> {
                    JSONObject temp = new JSONObject();
                    temp.putOpt("名称", entity.getFoodName());
                    temp.putOpt("类别", entity.getCategory());
                    temp.putOpt("重量", entity.getWeight());
                    temp.putOpt("热量", entity.getCalories());
                    food.add(temp);
                });
        return food;
    }

    /**
     * 根据submitId查询当前问卷的updateDate
     * 根据updateDate查询当天结果
     */
    @RequestMapping("sports")
    public Map<String, Object> sports(@RequestBody ModifyQueryDto dto) {
        if (StrUtil.isBlank(dto.getSubmitId())) {
            return ResultUtils.returnError("缺少submitId");
        }
        QuestionnaireSubmitInformation byId = submitInformationService.getById(dto.getSubmitId());
        QueryDto queryDto = new QueryDto();
        queryDto.setUserId(byId.getUserId());
        if (StrUtil.isNotBlank(dto.getQueryDate())) {
            queryDto.setDate(dto.getQueryDate());
        } else {
            queryDto.setDate(byId.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        SportResultVo vo = sportsResultService.getVo(queryDto);
        ManagerSport result = new ManagerSport();
        BeanUtil.copyProperties(vo, result);
        List<ManagerSportChild> list = new ArrayList<>();
        loadSports(vo.getBeforeSports(), "运动前热身", list);
        loadSports(vo.getSpecialSports(), "具体的运动", list);
        loadSports(vo.getAfterSports(), "运动后拉伸", list);
        result.setContents(list);
        return ResultUtils.returnSuccess(result);
    }

    private void loadSports(List<UrlAdvice> vo, String type, List<ManagerSportChild> list) {
        for (UrlAdvice afterSport : vo) {
            ManagerSportChild temp = new ManagerSportChild();
            BeanUtil.copyProperties(afterSport, temp);
            temp.setType(type);
            list.add(temp);
        }
    }


    /**
     * 根据submitId查询当前问卷的updateDate
     * 根据updateDate查询当天结果
     */
    @RequestMapping("updateSports")
    public Map<String, Object> updateSports(@RequestBody ManagerSport vo) {
        try {
            SportsResult sportsResult = new SportsResult();
            sportsResult.setId(vo.getId());
            sportsResult.setRDate(LocalDate.parse(vo.getNowDay(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            sportsResult.setUserId(vo.getUserId());
            JSONArray array = new JSONArray();
            for (ContentAdvice contentAdvice : vo.getHealthEducation()) {
                JSONObject temp = new JSONObject();
                temp.putOpt(contentAdvice.getName(), contentAdvice.getContent());
                array.add(temp);
            }
            sportsResult.setHealthEducation(array.toString());
            JSONArray before = new JSONArray();
            JSONArray after = new JSONArray();
            JSONArray specific = new JSONArray();
            for (ManagerSportChild beforeSport : vo.getContents()) {
                if ("运动前热身".equals(beforeSport.getType())) {
                    JSONObject first = new JSONObject();
                    JSONObject child = new JSONObject();
                    child.putOpt("名称", beforeSport.getName());
                    first.putOpt("推荐热身运动持续时间", beforeSport.getTime());
                    child.putOpt("视频", beforeSport.getUrl());
                    first.putOpt("推荐热身运动信息", child);
                    before.add(first);
                } else if ("运动后拉伸".equals(beforeSport.getType())) {
                    JSONObject first = new JSONObject();
                    JSONObject child = new JSONObject();
                    child.putOpt("名称", beforeSport.getName());
                    first.putOpt("运动后拉伸持续时间", beforeSport.getTime());
                    child.putOpt("视频", beforeSport.getUrl());
                    first.putOpt("运动后拉伸信息", child);
                    after.add(first);
                } else if ("具体的运动".equals(beforeSport.getType())) {
                    JSONObject first = new JSONObject();
                    JSONObject child = new JSONObject();
                    child.putOpt("名称", beforeSport.getName());
                    first.putOpt("推荐具体运动持续时间", beforeSport.getTime());
                    child.putOpt("视频", beforeSport.getUrl());
                    first.putOpt("推荐具体运动信息", child);
                    specific.add(first);
                }
            }
            sportsResult.setWarmUpBeforeExercise(before.toString());
            sportsResult.setSpecificSports(specific.toString());
            sportsResult.setStretchingAfterExercise(after.toString());
            sportsResultService.saveOrUpdateById(sportsResult);
        } catch (Exception e) {
            log.error("",e);
            return ResultUtils.returnError(e.getMessage() + "->funcName:updateSports");
        }
        return ResultUtils.returnSuccess("提交成功");
    }

    /**
     * 根据 submitId查groupID 查结果
     *
     * @return
     */
    @RequestMapping("psychology")
    public Map<String, Object> psychology(@RequestBody ModifyQueryDto dto) {
        if (StrUtil.isBlank(dto.getSubmitId())) {
            return ResultUtils.returnError("缺少submitId");
        }
        PsychologyResultVo vo = psychologyResultService.getVoBySubmitId(dto.getSubmitId());
        if (vo.getEvaluationTitle() != null) {
            vo.setEvaluationResults(vo.getEvaluationTitle() + vo.getEvaluationResults());
        }
        return ResultUtils.returnSuccess(vo);
    }

    /**
     * 根据id更新数据
     */
    @RequestMapping("updatePsychology")
    public Map<String, Object> updatePsychology(@RequestBody PsychologyResultVo vo) {
        try {
            PsychologyResult psychologyResult = new PsychologyResult();
            psychologyResult.setId(vo.getId());
            psychologyResult.setResult(vo.getEvaluationResults());
            JSONArray advice = new JSONArray();
            for (ContentAdvice contentAdvice : vo.getPsychologicalAdvices()) {
                JSONObject temp = new JSONObject();
                JSONArray a = new JSONArray();
                a.add(contentAdvice.getContent());
                temp.putOpt(contentAdvice.getName(), a);
                advice.add(temp);
            }
            psychologyResult.setAdvice(advice.toString());
//            JSONObject health = new JSONObject();
//            health.putOpt("建议", vo.getHealthContent());
//            JSONArray healthAdvice = new JSONArray();
//            for (UrlAdvice url : vo.getHealthAdvices()) {
//                JSONObject temp = new JSONObject();
//                temp.putOpt(url.getName(), url.getUrl());
//                healthAdvice.put(temp);
//            }
//            health.putOpt("练习", healthAdvice.toString());
//            psychologyResult.setHealthEducation(health.toString());
            psychologyResultService.saveOrUpdateById(psychologyResult);
        } catch (Exception e) {
            log.error("",e);
            return ResultUtils.returnError(e.getMessage() + "->funcName:updatePsychology");
        }
        return ResultUtils.returnSuccess("提交成功");
    }

    /**
     * 根据submitId查groupId 查看结果
     */
    @RequestMapping("medicine")
    public Map<String, Object> medicine(@RequestBody ModifyQueryDto dto) {
        if (StrUtil.isBlank(dto.getSubmitId())) {
            return ResultUtils.returnError("缺少submitId");
        }
        ChineseMedicineVo vo = chineseMedicineService.getVoBySubmitId(dto.getSubmitId());
        ManagerMedicine result = new ManagerMedicine();
        BeanUtil.copyProperties(vo, result);
        List<ManagerPoint> list = new ArrayList<>();
        for (MassageVo massageVo : result.getMassageList()) {
            for (UrlAdvice urlAdvice : massageVo.getAcupuncturePointsList()) {
                ManagerPoint temp = new ManagerPoint();
                BeanUtil.copyProperties(urlAdvice, temp);
                temp.setType(massageVo.getName());
                list.add(temp);
            }
        }
        result.setPoints(list);
        result.setMassageList(null);
        return ResultUtils.returnSuccess(result);
    }

    /**
     * 根据id更新数据
     */
    @RequestMapping("updateMedicine")
    public Map<String, Object> updateMedicine(@RequestBody ManagerMedicine vo) {
        try {
            ChineseMedicine medicine = new ChineseMedicine();
            medicine.setId(vo.getId());
            JSONObject xuewei = new JSONObject();
            JSONArray tou = new JSONArray();
            JSONArray shang = new JSONArray();
            JSONArray xia = new JSONArray();
            JSONArray fu = new JSONArray();
            JSONArray er = new JSONArray();
            String touStr = "头颈部穴位按摩";
            String shangStr = "上肢穴位按摩";
            String xiaStr = "下肢穴位按摩";
            String fuStr = "腹部穴位按摩";
            String erStr = "耳部穴位按摩";
            for (ManagerPoint point : vo.getPoints()) {
                if (touStr.equals(point.getType())) {
                    JSONObject temp = new JSONObject();
                    temp.putOpt(point.getName(), point.getUrl());
                    tou.add(temp);
                } else if (shangStr.equals(point.getType())) {
                    JSONObject temp = new JSONObject();
                    temp.putOpt(point.getName(), point.getUrl());
                    shang.add(temp);
                } else if (xiaStr.equals(point.getType())) {
                    JSONObject temp = new JSONObject();
                    temp.putOpt(point.getName(), point.getUrl());
                    xia.add(temp);
                } else if (fuStr.equals(point.getType())) {
                    JSONObject temp = new JSONObject();
                    temp.putOpt(point.getName(), point.getUrl());
                    fu.add(temp);
                } else if (erStr.equals(point.getType())) {
                    JSONObject temp = new JSONObject();
                    temp.putOpt(point.getName(), point.getUrl());
                    shang.add(temp);
                }
            }
            xuewei.putOpt(touStr, tou);
            xuewei.putOpt(shangStr, shang);
            xuewei.putOpt(xiaStr, xia);
            xuewei.putOpt(fuStr, fu);
            xuewei.putOpt(erStr, er);
            medicine.setAcupressure(xuewei.toString());
            JSONArray music = new JSONArray();
            for (UrlAdvice urlAdvice : vo.getMusicList()) {
                JSONObject temp = new JSONObject();
                temp.putOpt(urlAdvice.getName(), urlAdvice.getUrl());
                music.add(temp);
            }
            medicine.setFiveElementsMusic(music.toString());
            chineseMedicineService.saveOrUpdateById(medicine);
        } catch (Exception e) {
            log.error("",e);
            return ResultUtils.returnError(e.getMessage() + "->funcName:updateMedicine");
        }
        return ResultUtils.returnSuccess("提交成功");
    }

}
