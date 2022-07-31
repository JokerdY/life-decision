package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.life.decision.support.dto.SportsResultDto;
import com.life.decision.support.mapper.SportsResultMapper;
import com.life.decision.support.pojo.SportsResult;
import com.life.decision.support.service.ISportsResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportsResultServiceImpl implements ISportsResultService {
    @Autowired
    SportsResultMapper mapper;

    public void saveOrUpdate(SportsResult entity) {
        SportsResultDto sportsResultDto = new SportsResultDto();
        BeanUtil.copyProperties(entity,sportsResultDto);
        if (mapper.selectByEntity(sportsResultDto) != null) {
            mapper.updateByPrimaryKeySelective(entity);
        } else {
            mapper.insert(entity);
        }
    }

    public int delete(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int save(SportsResult result) {
        return mapper.insert(result);
    }

    public int saveSelective(SportsResult result) {
        return mapper.insertSelective(result);
    }

    public SportsResult findByEntity(SportsResultDto result) {
        return mapper.selectByEntity(result);
    }

    public List<SportsResult> listByEntity(SportsResult result) {
        return mapper.listByEntity(result);
    }

    public List<String> listByYearAndMouth(String yearAndMouth, String userId) {
        return mapper.listByYearAndMouth(yearAndMouth, userId);
    }
}
