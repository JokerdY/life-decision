package com.life.decision.support.service.impl;

import com.life.decision.support.mapper.RecipeResultMapper;
import com.life.decision.support.pojo.RecipeResult;
import com.life.decision.support.service.IRecipeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeResultServiceImpl implements IRecipeResultService {
    @Autowired
    RecipeResultMapper mapper;

    public void saveOrUpdate(RecipeResult entity) {
        if (mapper.selectByEntity(entity) != null) {
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

    public RecipeResult findByEntity(RecipeResult result) {
        return mapper.selectByEntity(result);
    }

    public List<RecipeResult> listByEntity(RecipeResult result) {
        return mapper.listByEntity(result);
    }
}
