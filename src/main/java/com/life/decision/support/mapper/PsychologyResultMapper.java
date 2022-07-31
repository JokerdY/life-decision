package com.life.decision.support.mapper;

import com.life.decision.support.pojo.PsychologyResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PsychologyResultMapper {

    int deleteByPrimaryKey(String id);

    int insert(PsychologyResult record);

    int insertSelective(PsychologyResult record);

    PsychologyResult selectByEntity(PsychologyResult entity);

    int updateByPrimaryKeySelective(PsychologyResult record);

    List<PsychologyResult> listByEntity(PsychologyResult entity);
}
