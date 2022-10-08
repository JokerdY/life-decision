package com.life.decision.support.mapper;

import com.life.decision.support.dto.UrlAccessDetailsDto;
import com.life.decision.support.pojo.UrlAccessDetails;
import com.life.decision.support.vo.UrlAccessCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UrlAccessDetailsMapper {
    int insert(UrlAccessDetails entity);

    List<UrlAccessCountVo> selectVoList(UrlAccessDetailsDto entity);

    List<String> selectDistinctName();
}
