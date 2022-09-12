package com.life.decision.support.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.life.decision.support.mapper.PsychologicalOutcomeMapper;
import com.life.decision.support.pojo.PsychologicalOutcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PsychologicalOutcomeService {
    @Autowired
    PsychologicalOutcomeMapper mapper;

    public List<PsychologicalOutcome> findList(PsychologicalOutcome psychologicalOutcome) {
        return mapper.listByEntity(psychologicalOutcome);
    }

    public List<PsychologicalOutcome> selectLatestByEntity(PsychologicalOutcome outcome) {
        return mapper.selectLatestByEntity(outcome);
    }

    public int save(PsychologicalOutcome psychologicalOutcome) {
        if (StrUtil.isBlank(psychologicalOutcome.getId())) {
            psychologicalOutcome.setId(IdUtil.fastSimpleUUID());
        }
        return mapper.insert(psychologicalOutcome);
    }

}
