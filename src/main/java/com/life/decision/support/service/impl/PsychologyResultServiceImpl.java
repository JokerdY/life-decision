package com.life.decision.support.service.impl;

import com.life.decision.support.mapper.PsychologyResultMapper;
import com.life.decision.support.pojo.PsychologyResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PsychologyResultServiceImpl {
    @Autowired
    PsychologyResultMapper mapper;

    public void saveOrUpdate(PsychologyResult entity) {
        if (mapper.selectByEntity(entity) != null) {
            mapper.updateByPrimaryKeySelective(entity);
        } else {
            mapper.insert(entity);
        }
    }

    public int delete(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int save(PsychologyResult result) {
        return mapper.insert(result);
    }

    public int saveSelective(PsychologyResult result) {
        return mapper.insertSelective(result);
    }

    public PsychologyResult findByEntity(PsychologyResult result) {
        return mapper.selectByEntity(result);
    }

    public List<PsychologyResult> listByEntity(PsychologyResult result) {
        return mapper.listByEntity(result);
    }
}
