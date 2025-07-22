package com.life.decision.support.mapper;

import com.life.decision.support.dto.SportsResultDto;
import com.life.decision.support.pojo.SportsResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SportsResultMapper {

    int deleteByPrimaryKey(String id);

    int insert(SportsResult record);

    int insertSelective(SportsResult record);

    SportsResult selectByEntity(SportsResultDto entity);

    int updateByPrimaryKeySelective(SportsResult record);

    List<SportsResult> listByEntity(SportsResult entity);

    List<String> listByYearAndMouth(@Param("yearAndMouth") String yearAndMouth,
                                    @Param("userId") String userId);

    SportsResult selectById(SportsResult sportsResult);
}
