package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.dto.SportsResultDto;
import com.life.decision.support.mapper.SportsResultMapper;
import com.life.decision.support.pojo.SportsResult;
import com.life.decision.support.service.ISportsResultService;
import com.life.decision.support.bo.UrlAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SportsResultServiceImpl implements ISportsResultService {
    @Autowired
    SportsResultMapper mapper;

    public void saveOrUpdate(SportsResult entity) {
        SportsResultDto sportsResultDto = new SportsResultDto();
        BeanUtil.copyProperties(entity, sportsResultDto);
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

    public UrlAdvice getAdvice(String userId) {
        SportsResultDto resultDto = new SportsResultDto();
        resultDto.setUserId(userId);
        resultDto.setQueryDate(LocalDate.now().toString());
        SportsResult byEntity = findByEntity(resultDto);
        if (byEntity != null) {
            List<UrlAdvice> specialList = getAdviceListTempLate(byEntity.getSpecificSports(), "推荐具体运动信息", "推荐具体运动持续时间");
            if (specialList.size() > 0) {
                return specialList.get(LocalDate.now().getDayOfYear() % specialList.size());
            }
        }
        return new UrlAdvice(null, null, null);
    }

    public List<UrlAdvice> getAdviceListTempLate(String byEntity, String msgItem, String timeItem) {
        List<UrlAdvice> list = new ArrayList<>();
        JSONArray array = JSONUtil.parseArray(byEntity);
        for (Object o : array) {
            JSONObject temp = (JSONObject) o;
            JSONObject msg = temp.getJSONObject(msgItem);
            String name = msg.getStr("名称");
            String time = temp.getStr(timeItem);
            String url = msg.getStr("视频");
            list.add(new UrlAdvice(name, time, url));
        }
        return list;
    }
}
