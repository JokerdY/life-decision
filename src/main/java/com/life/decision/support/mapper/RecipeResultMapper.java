package com.life.decision.support.mapper;

import com.life.decision.support.pojo.RecipeResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeResultMapper {

    int deleteByPrimaryKey(String id);

    int insert(RecipeResult record);

    int insertSelective(RecipeResult record);

    RecipeResult selectByEntity(RecipeResult entity);

    int updateByPrimaryKeySelective(RecipeResult record);

    List<RecipeResult> listByEntity(RecipeResult entity);

}
