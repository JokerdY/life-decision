package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.bo.FoodEntity;
import com.life.decision.support.bo.RecipeEntity;
import com.life.decision.support.bo.TotalCaloriesEntity;
import com.life.decision.support.dto.QueryDto;
import com.life.decision.support.dto.RecipeResultDto;
import com.life.decision.support.mapper.RecipeResultMapper;
import com.life.decision.support.pojo.RecipeResult;
import com.life.decision.support.service.IRecipeResultService;
import com.life.decision.support.vo.RecipeResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeResultServiceImpl implements IRecipeResultService {
    @Autowired
    RecipeResultMapper mapper;

    public RecipeResultVo getResultVo(QueryDto dto) {
        // 获取当天的数据
        RecipeResultDto resultDto = new RecipeResultDto();
        resultDto.setUserId(dto.getUserId());
        resultDto.setQueryDate(dto.getDate());
        RecipeResult byEntity = findByEntity(resultDto);
        RecipeResultVo vo = new RecipeResultVo();
        vo.setDateRecord(getRecipeDateRecord(dto));
        if (byEntity != null) {
            loadRecipeVo(dto, byEntity, vo);
        } else {
            vo.setNowDay(dto.getDate());
            vo.setUserId(dto.getUserId());
            vo.setId(IdUtil.fastSimpleUUID());
            TotalCaloriesEntity totalCaloriesEntity = new TotalCaloriesEntity();
            totalCaloriesEntity.setEntityList(new ArrayList<>());
            vo.setTotalCaloriesEntity(totalCaloriesEntity);
            vo.setHealthEducation(new ArrayList<>());
            RecipeEntity recipeEntity = new RecipeEntity();
            recipeEntity.setFoodEntities(new ArrayList<>());
            vo.setBreakfastRecipe(recipeEntity);
            vo.setLunchRecipe(recipeEntity);
            vo.setDinnerRecipe(recipeEntity);
            vo.setDietaryAdvice(new ArrayList<>());
        }
        return vo;
    }

    public List<String> getRecipeDateRecord(QueryDto dto) {
        if (StrUtil.isBlank(dto.getDate())) {
            dto.setDate(LocalDate.now().toString());
        }
        String yearAndMouth = LocalDate.parse(dto.getDate()).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        // 获取当月的所有记录
        return listByYearAndMouth(yearAndMouth, dto.getUserId());
    }

    public List<ContentAdvice> getHealthEducation(String byEntity) {
        if (StrUtil.isBlank(byEntity)) {
            return new ArrayList<>();
        }
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

    public void loadRecipeVo(QueryDto dto, RecipeResult recipeResult, RecipeResultVo vo) {
        vo.setNowDay(dto.getDate());
        vo.setUserId(dto.getUserId());
        vo.setId(recipeResult.getId());
        vo.setTotalCaloriesEntity(getTotalCaloriesEntity(recipeResult));
        vo.setHealthEducation(getHealthEducation(recipeResult.getHealthEducation()));
        vo.setBreakfastRecipe(getRecipeEntity(recipeResult.getBreakfast()));
        vo.setLunchRecipe(getRecipeEntity(recipeResult.getLunch()));
        vo.setDinnerRecipe(getRecipeEntity(recipeResult.getDinner()));
        if (StrUtil.isNotBlank(recipeResult.getDietaryAdvice())) {
            vo.setDietaryAdvice(JSONUtil.parseArray(recipeResult.getDietaryAdvice()).toList(String.class));
        }
        if (vo.getBreakfastRecipe().getTotalCalories() != null && vo.getDinnerRecipe().getTotalCalories() != null && vo.getLunchRecipe().getTotalCalories() != null) {
            String total = new DecimalFormat("0").format(vo.getBreakfastRecipe().getTotalCaloriesDouble()
                    + vo.getDinnerRecipe().getTotalCaloriesDouble()
                    + vo.getLunchRecipe().getTotalCaloriesDouble());
            vo.getTotalCaloriesEntity().setTotalCalories(total);
        }
    }

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
        RecipeResultDto resultDto = new RecipeResultDto();
        resultDto.setUserId(userId);
        resultDto.setQueryDate(LocalDate.now().toString());
        ContentAdvice contentAdvice = new ContentAdvice(null, null);
        RecipeResult recipeResult = findByEntity(resultDto);
        if (recipeResult != null) {
            RecipeResultVo vo = new RecipeResultVo();
            vo.setBreakfastRecipe(getRecipeEntity(recipeResult.getBreakfast()));
            vo.setLunchRecipe(getRecipeEntity(recipeResult.getLunch()));
            vo.setDinnerRecipe(getRecipeEntity(recipeResult.getDinner()));
            if (vo.getBreakfastRecipe().getTotalCalories() != null && vo.getDinnerRecipe().getTotalCalories() != null && vo.getLunchRecipe().getTotalCalories() != null) {
                String total = new DecimalFormat("0").format(vo.getBreakfastRecipe().getTotalCaloriesDouble()
                        + vo.getDinnerRecipe().getTotalCaloriesDouble()
                        + vo.getLunchRecipe().getTotalCaloriesDouble());
                contentAdvice = new ContentAdvice(total, total);
            }
        }
        return contentAdvice;
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
                builder.food(new FoodEntity(temp.getStr("名称"), temp.getStr("重量"), temp.getStr("热量"), temp.getStr("类别")));
            }
        } catch (Throwable e) {
            log.error("getRecipe:", e);
        }
        return builder.build();
    }

    public void saveOrUpdateById(RecipeResult recipeResult) {
        if (mapper.selectById(recipeResult) != null) {
            mapper.updateByPrimaryKeySelective(recipeResult);
        } else {
            mapper.insert(recipeResult);
        }
    }
}
