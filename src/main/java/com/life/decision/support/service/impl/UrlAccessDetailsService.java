package com.life.decision.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.life.decision.support.dto.UrlAccessDetailsDto;
import com.life.decision.support.enums.UrlAccessType;
import com.life.decision.support.mapper.UrlAccessDetailsMapper;
import com.life.decision.support.pojo.UrlAccessDetails;
import com.life.decision.support.vo.UrlAccessCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UrlAccessDetailsService {
    @Autowired
    UrlAccessDetailsMapper mapper;

    public int saveApi(UrlAccessDetails accessDetails) {
        return mapper.insert(accessDetails);
    }

    public PageInfo<UrlAccessCountVo> pageCountVo(UrlAccessDetailsDto dto) {
        PageHelper.startPage(dto);
        PageInfo<UrlAccessCountVo> page = PageInfo.of(mapper.selectVoList(dto));
        for (UrlAccessCountVo vo : page.getList()) {
            vo.setTypeName(Arrays.stream(UrlAccessType.values())
                    .filter(s -> s.getId().equals(vo.getType()))
                    .findAny().orElse(UrlAccessType.VIDEO)
                    .getName());
        }
        return page;
    }

    public List<String> distinctNameList(){
        return mapper.selectDistinctName();
    }
}
