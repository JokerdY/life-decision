package com.life.decision.support.mapper;

import com.life.decision.support.pojo.PsychologicalOutcome;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PsychologicalOutcomeMapper {
    List<PsychologicalOutcome> listByEntity(PsychologicalOutcome psychologicalOutcome);

    List<PsychologicalOutcome> selectLatestByEntity(PsychologicalOutcome psychologicalOutcome);

    int insert(PsychologicalOutcome psychologicalOutcome);
}
