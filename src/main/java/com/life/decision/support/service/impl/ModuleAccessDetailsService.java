package com.life.decision.support.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.life.decision.support.dto.ModuleAccessDetailsDto;
import com.life.decision.support.enums.ModuleType;
import com.life.decision.support.mapper.ModuleAccessDetailsMapper;
import com.life.decision.support.pojo.ModuleAccessDetails;
import com.life.decision.support.vo.DataCountByMouthVo;
import com.life.decision.support.vo.ModuleAccessDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ModuleAccessDetailsService {
    @Autowired
    ModuleAccessDetailsMapper mapper;

    public int saveApi(ModuleType type, String api, String userId) {
        ModuleAccessDetails temp = new ModuleAccessDetails();
        temp.setId(IdUtil.fastSimpleUUID());
        temp.setApi(api);
        temp.setUserId(userId);
        temp.setType(type.getType());
        return mapper.insert(temp);
    }

    public PageInfo<ModuleAccessDetailsVo> pageVo(ModuleAccessDetailsDto dto) {
        PageHelper.startPage(dto);
        PageInfo<ModuleAccessDetailsVo> page = PageInfo.of(mapper.selectVoListWithOutAccess(dto));
        for (ModuleAccessDetailsVo vo : page.getList()) {
            vo.setTypeName(Arrays.stream(ModuleType.values())
                    .filter(s -> s.getType().equals(vo.getType()))
                    .findAny().orElse(ModuleType.RECIPE)
                    .getName());
        }
        return page;
    }

    public List<DataCountByMouthVo> findTotalAccessVo(String startDate, String endDate) {
        return mapper.findUserRegisterVo(startDate, endDate);
    }
}
