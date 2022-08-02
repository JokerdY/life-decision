package com.life.decision.support.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.mapper.PsychologyResultMapper;
import com.life.decision.support.pojo.PsychologyResult;
import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.vo.PsychologyResultVo;
import com.life.decision.support.bo.UrlAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PsychologyResultServiceImpl {
    @Autowired
    PsychologyResultMapper mapper;

    public void saveOrUpdate(PsychologyResult entity) {
        if (mapper.selectByEntity(entity) != null) {
            mapper.updateByPrimaryKeySelective(entity);
        } else {
            mapper.insert(entity);
        }
    }

    public int delete(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int save(PsychologyResult result) {
        return mapper.insert(result);
    }

    public int saveSelective(PsychologyResult result) {
        return mapper.insertSelective(result);
    }

    public PsychologyResult findByEntity(PsychologyResult result) {
        return mapper.selectByEntity(result);
    }

    public List<PsychologyResult> listByEntity(PsychologyResult result) {
        return mapper.listByEntity(result);
    }

    public ContentAdvice getAdvice(String userId) {
        PsychologyResult dto = new PsychologyResult();
        dto.setUserId(userId);
        PsychologyResult byEntity = findByEntity(dto);
        if (byEntity != null) {
            List<ContentAdvice> psychologicalAdvices = getPsychologicalAdvices(JSONUtil.parseArray(byEntity.getAdvice()));
            if (psychologicalAdvices.size() > 0) {
                return psychologicalAdvices.get(LocalDate.now().getDayOfYear() % psychologicalAdvices.size());
            }
        }
        return new ContentAdvice(null, null);
    }

    public PsychologyResultVo getVo(PsychologyResult dto) {
        PsychologyResultVo vo = new PsychologyResultVo();
        PsychologyResult byEntity = findByEntity(dto);
        if (byEntity != null) {
            JSONArray adviceArray = JSONUtil.parseArray(byEntity.getAdvice());
            vo.setPsychologicalAdvices(getPsychologicalAdvices(adviceArray));
            String result = byEntity.getResult();
            if (result.contains("存在")) {
                String[] split = result.split("存在", 2);
                vo.setEvaluationTitle(split[0]);
                vo.setEvaluationResults(split[1]);
            } else {
                vo.setEvaluationResults(result);
            }
            JSONObject health = JSONUtil.parseObj(byEntity.getHealthEducation());
            vo.setHealthAdvices(health.getJSONArray("练习").stream()
                    .map(obj -> {
                        JSONObject json = (JSONObject) obj;
                        Set<String> keySet = json.keySet();
                        if (keySet.size() == 1) {
                            for (String k : keySet) {
                                return new UrlAdvice(k, null, json.getStr(k));
                            }
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
            vo.setHealthContent(health.getStr("建议"));
        }
        return vo;
    }

    private List<ContentAdvice> getPsychologicalAdvices(JSONArray adviceArray) {
        return adviceArray.stream().map(obj -> {
                    JSONObject json = (JSONObject) obj;
                    Set<String> keySet = json.keySet();
                    if (keySet.size() == 2) {
                        for (String k : keySet) {
                            if (!"参考资料".equals(k)) {
                                JSONArray objects = JSONUtil.parseArray(json.getStr(k));
                                if (objects.size() == 1) {
                                    return new ContentAdvice(k, objects.get(0, String.class));
                                }
                            }
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
