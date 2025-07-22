package com.life.decision.support.mapper;

import com.life.decision.support.pojo.ChineseMedicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChineseMedicineMapper {

    int deleteByPrimaryKey(String id);

    int insert(ChineseMedicine record);

    int insertSelective(ChineseMedicine record);

    ChineseMedicine selectByEntity(ChineseMedicine entity);

    int updateByPrimaryKeySelective(ChineseMedicine record);

    List<ChineseMedicine> listByEntity(ChineseMedicine entity);

    ChineseMedicine selectBySubmitId(@Param("submitId") String submitId);

    ChineseMedicine selectById(ChineseMedicine entity);
}
