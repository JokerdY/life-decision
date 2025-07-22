package com.life.decision.support.mapper;

import com.life.decision.support.dto.ModuleAccessDetailsDto;
import com.life.decision.support.pojo.ModuleAccessDetails;
import com.life.decision.support.vo.DataCountByMouthVo;
import com.life.decision.support.vo.ModuleAccessCountVo;
import com.life.decision.support.vo.ModuleAccessDetailsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ModuleAccessDetailsMapper {
    int insert(ModuleAccessDetails entity);

    List<ModuleAccessCountVo> selectCountVoList(ModuleAccessDetailsDto entity);

    List<ModuleAccessDetailsVo> selectVoListWithOutAccess(ModuleAccessDetailsDto entity);

    List<DataCountByMouthVo> findUserRegisterVo(@Param("startDate") String startDate,
                                                @Param("endDate") String endDate);
}