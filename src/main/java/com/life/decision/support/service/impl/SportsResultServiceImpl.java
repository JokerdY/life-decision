package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.dto.QueryDto;
import com.life.decision.support.dto.SportsResultDto;
import com.life.decision.support.mapper.SportsResultMapper;
import com.life.decision.support.pojo.SportsResult;
import com.life.decision.support.service.ISportsResultService;
import com.life.decision.support.bo.UrlAdvice;
import com.life.decision.support.vo.SportResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void saveOrUpdateById(SportsResult sportsResult) {
        if (mapper.selectById(sportsResult) != null) {
            mapper.updateByPrimaryKeySelective(sportsResult);
        } else {
            mapper.insert(sportsResult);
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
            }else {
                return new UrlAdvice("无相关建议","","");
            }
        }
        return new UrlAdvice(null, null, null);
    }

    public List<UrlAdvice> getAdviceListTempLate(String byEntity, String msgItem, String timeItem) {
        List<UrlAdvice> list = new ArrayList<>();
        if (!StrUtil.isBlank(byEntity)) {
            JSONArray array = JSONUtil.parseArray(byEntity);
            for (Object o : array) {
                JSONObject temp = (JSONObject) o;
                JSONObject msg = temp.getJSONObject(msgItem);
                if (msg == null) {
                    continue;
                }
                String name = msg.getStr("名称");
                String timeStr = temp.getStr(timeItem);
                String time = timeStr;
                if (timeStr.contains(".")) {
                    time = timeStr.split("\\.")[0];
                }
                String url = msg.getStr("视频");
                list.add(new UrlAdvice(name, time, url));
            }
        }
        return list;
    }

    public List<String> getSportsDateRecord(QueryDto dto) {
        if (StrUtil.isBlank(dto.getDate())) {
            dto.setDate(LocalDate.now().toString());
        }
        String yearAndMouth = LocalDate.parse(dto.getDate()).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        // 获取当月的所有记录
        return listByYearAndMouth(yearAndMouth, dto.getUserId());
    }

    public SportResultVo getVo(QueryDto dto) {
        SportsResultDto resultDto = new SportsResultDto();
        resultDto.setUserId(dto.getUserId());
        resultDto.setQueryDate(dto.getDate());
        SportsResult byEntity = findByEntity(resultDto);
        SportResultVo sportResultVo = new SportResultVo();
        sportResultVo.setDateRecord(getSportsDateRecord(dto));
        sportResultVo.setNowDay(dto.getDate());
        sportResultVo.setUserId(dto.getUserId());
        if (byEntity != null) {
            sportResultVo.setId(byEntity.getId());
            List<UrlAdvice> beforeList = getAdviceListTempLate(byEntity.getWarmUpBeforeExercise(), "推荐热身运动信息", "推荐热身运动持续时间");
            sportResultVo.setBeforeSports(beforeList);
            List<UrlAdvice> specialList = getAdviceListTempLate(byEntity.getSpecificSports(), "推荐具体运动信息", "推荐具体运动持续时间");
            sportResultVo.setSpecialSports(specialList);
            List<UrlAdvice> afterList = getAdviceListTempLate(byEntity.getStretchingAfterExercise(), "运动后拉伸信息", "运动后拉伸持续时间");
            sportResultVo.setAfterSports(afterList);
            sportResultVo.setHealthEducation(getHealthEducation(byEntity.getHealthEducation()));
        } else {
            sportResultVo.setId(IdUtil.fastSimpleUUID());
            sportResultVo.setAfterSports(new ArrayList<>());
            sportResultVo.setBeforeSports(new ArrayList<>());
            sportResultVo.setSpecialSports(new ArrayList<>());
            sportResultVo.setDateRecord(new ArrayList<>());
            sportResultVo.setHealthEducation(new ArrayList<>());
        }
        return sportResultVo;
    }

    public List<ContentAdvice> getHealthEducation(String byEntity) {
        if (StrUtil.isBlank(byEntity)) {
            return new ArrayList<>();
        }
        return JSONUtil.parseArray(byEntity).stream()
                .map((obj) -> {
                    JSONObject temp = (JSONObject) obj;
                    Set<String> keySet = temp.keySet();
                    if (keySet.size() > 0) {
                        String[] key = keySet.toArray(new String[2]);
                        return new ContentAdvice(key[0], temp.getStr(key[0]));
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
