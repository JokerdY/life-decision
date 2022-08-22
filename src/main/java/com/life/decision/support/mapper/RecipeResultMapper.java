package com.life.decision.support.mapper;

import com.life.decision.support.dto.RecipeResultDto;
import com.life.decision.support.pojo.RecipeResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeResultMapper {

    int deleteByPrimaryKey(String id);

    int insert(RecipeResult record);

    int insertSelective(RecipeResult record);

    RecipeResult selectByEntity(RecipeResultDto entity);

    int updateByPrimaryKeySelective(RecipeResult record);

    List<RecipeResult> listByEntity(RecipeResult entity);

    List<String> listByYearAndMouth(String yearAndMouth, String userId);

    RecipeResult selectById(RecipeResult recipeResult);
}
