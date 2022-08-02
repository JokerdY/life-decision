package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.dto.RecipeResultDto;
import com.life.decision.support.mapper.RecipeResultMapper;
import com.life.decision.support.pojo.RecipeResult;
import com.life.decision.support.service.IRecipeResultService;
import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.bo.FoodEntity;
import com.life.decision.support.bo.RecipeEntity;
import com.life.decision.support.bo.TotalCaloriesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RecipeResultServiceImpl implements IRecipeResultService {
    @Autowired
    RecipeResultMapper mapper;

    public void saveOrUpdate(RecipeResult entity) {
        RecipeResultDto dto = new RecipeResultDto();
        BeanUtil.copyProperties(entity, dto);
        if (mapper.selectByEntity(dto) != null) {
            mapper.updateByPrimaryKeySelective(entity);
        } else {
            mapper.insert(entity);
        }
    }

    public int delete(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int save(RecipeResult result) {
        return mapper.insert(result);
    }

    public int saveSelective(RecipeResult result) {
        return mapper.insertSelective(result);
    }

    public RecipeResult findByEntity(RecipeResultDto result) {
        return mapper.selectByEntity(result);
    }

    public List<RecipeResult> listByEntity(RecipeResult result) {
        return mapper.listByEntity(result);
    }

    public ContentAdvice getAdvice(String userId) {
        return null;
    }

    public List<String> listByYearAndMouth(String yearAndMouth, String userId) {
        return mapper.listByYearAndMouth(yearAndMouth, userId);
    }

    public TotalCaloriesEntity getTotalCaloriesEntity(RecipeResult byEntity) {
        JSONObject totalCal = JSONUtil.parseObj(byEntity.getTotalCalories());
        String proportion = "占比";
        String weight = "重量";
        String carbohydrate = "碳水化合物";
        String protein = "蛋白质";
        String fat = "脂肪";
        JSONObject carbohydrateObj = totalCal.getJSONObject(carbohydrate);
        JSONObject proteinObj = totalCal.getJSONObject(protein);
        JSONObject fatObj = totalCal.getJSONObject(fat);

        TotalCaloriesEntity totalCaloriesEntity = new TotalCaloriesEntity();
        List<TotalCaloriesEntity.ElementEntity> entityList = new ArrayList<>();

        entityList.add(new TotalCaloriesEntity.ElementEntity(carbohydrate, carbohydrateObj.getStr(proportion), carbohydrateObj.getStr(weight)));
        entityList.add(new TotalCaloriesEntity.ElementEntity(protein, proteinObj.getStr(proportion), proteinObj.getStr(weight)));
        entityList.add(new TotalCaloriesEntity.ElementEntity(fat, fatObj.getStr(proportion), fatObj.getStr(weight)));
        totalCaloriesEntity.setEntityList(entityList);
        return totalCaloriesEntity;
    }

    public RecipeEntity getRecipeEntity(String recipeStr) {
        RecipeEntity.Builder builder = new RecipeEntity.Builder().builder();
        try {
            JSONArray obj = JSONUtil.parseArray(recipeStr);
            for (Object childObj : obj) {
                JSONObject temp = (JSONObject) childObj;
                builder.food(new FoodEntity(temp.getStr("名称"), temp.getStr("重量"), temp.getStr("热量")));
            }
        } catch (Throwable e) {
            log.error("getRecipe:", e);
        }
        return builder.build();
    }
}
