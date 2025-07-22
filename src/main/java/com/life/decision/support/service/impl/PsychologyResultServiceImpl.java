package com.life.decision.support.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.http.PyHttp;
import com.life.decision.support.mapper.PsychologyResultMapper;
import com.life.decision.support.pojo.PsychologyResult;
import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import com.life.decision.support.vo.PsychologyResultVo;
import com.life.decision.support.bo.UrlAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PsychologyResultServiceImpl {
    @Autowired
    PsychologyResultMapper mapper;
    @Autowired
    PyHttp pyHttp;

    public int uploadScript() {
        int count = 0;
        List<PsychologyResult> psychologyResults = mapper.listByEntity(new PsychologyResult());
        for (PsychologyResult psychologyResult : psychologyResults) {
            if (StrUtil.isBlank(psychologyResult.getPhysical())) {
                String groupId = psychologyResult.getGroupId();
                try {
                    if (StrUtil.isNotBlank(groupId)) {
                        QuestionnaireGroupInformation entity = new QuestionnaireGroupInformation();
                        entity.setGroupId(groupId);
                        entity.setUserId(psychologyResult.getUserId());
                        JSONObject pyResult = pyHttp.getPyResult(entity);
                        psychologyResult.setPhysical(pyResult.getStr("Physical"));
                        count += mapper.updateByPrimaryKeySelective(psychologyResult);
                    }
                } catch (Exception e) {
                    log.error("历史数据导入异常", e);
                }
            }
        }
        return count;
    }


    public void saveOrUpdateByUserId(PsychologyResult entity) {
        if (mapper.selectByEntity(entity) != null) {
            mapper.updateByPrimaryKeySelective(entity);
        } else {
            mapper.insert(entity);
        }
    }

    public void saveOrUpdateById(PsychologyResult entity) {
        if (mapper.selectById(entity) != null) {
            mapper.updateByPrimaryKeySelective(entity);
        } else {
            mapper.insert(entity);
        }
    }

    public void updateByPrimaryKeySelective(PsychologyResult entity) {
        mapper.updateByPrimaryKeySelective(entity);
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
    public List<PsychologyResult> listDistinctUserByEntity(PsychologyResult result) {
        return mapper.listDistinctUserByEntity(result);
    }

    public ContentAdvice getAdvice(String userId) {
        PsychologyResult dto = new PsychologyResult();
        dto.setUserId(userId);
        PsychologyResult byEntity = findByEntity(dto);
        if (byEntity != null) {
            String advice = byEntity.getAdvice();
            log.info("advice:{}", advice);
            if (StrUtil.isNotBlank(advice)) {
                List<ContentAdvice> psychologicalAdvices = getPsychologicalAdvices(JSONUtil.parseArray(advice));
                log.info("psychologicalAdvices:{}", psychologicalAdvices);
                if (psychologicalAdvices.size() > 0) {
                    return psychologicalAdvices.get(LocalDate.now().getDayOfYear() % psychologicalAdvices.size());
                }
            }
        }
        return new ContentAdvice(null, null);
    }

    public PsychologyResultVo getVo(PsychologyResult dto) {
        PsychologyResult byEntity = findByEntity(dto);
        return getResultVo(byEntity);
    }

    public PsychologyResultVo getVoBySubmitId(String submitId) {
        PsychologyResult byEntity = mapper.selectBySubmitId(submitId);
        return getResultVo(byEntity);
    }

    private PsychologyResultVo getResultVo(PsychologyResult byEntity) {
        PsychologyResultVo vo = new PsychologyResultVo();
        if (byEntity != null) {
            JSONArray adviceArray = JSONUtil.parseArray(byEntity.getAdvice());
            vo.setId(byEntity.getId());
            vo.setPsychologicalAdvices(getPsychologicalAdvices(adviceArray));
            String result = byEntity.getResult();
            if (result.contains("存在")) {
                String[] split = result.split("存在", 2);
                vo.setEvaluationTitle(split[0] + "存在");
                vo.setEvaluationResults(split[1]);
            } else {
                vo.setEvaluationResults(result);
            }
            JSONObject health = JSONUtil.parseObj(byEntity.getHealthEducation());
            if (!health.isNull("练习")) {
                JSONArray exe = health.getJSONArray("练习");
                vo.setHealthAdvices(exe.stream()
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
            }
            vo.setHealthContent(health.getStr("建议"));
        } else {
            vo.setId(IdUtil.fastSimpleUUID());
            vo.setHealthAdvices(new ArrayList<>());
            vo.setPsychologicalAdvices(new ArrayList<>());
        }
        return vo;
    }

    private List<ContentAdvice> getPsychologicalAdvices(JSONArray adviceArray) {
        return adviceArray.stream().map(obj -> {
                    JSONObject json = (JSONObject) obj;
                    Set<String> keySet = json.keySet();
                    for (String k : keySet) {
                        if (!"参考资料".equals(k)) {
                            String str = json.getStr(k);
                            try {
                                JSONArray objects = JSONUtil.parseArray(str);
                                if (objects.size() > 0) {
                                    if (objects.size() == 1) {
                                        return new ContentAdvice(k, objects.get(0, String.class));
                                    } else {
                                        return new ContentAdvice(k, (String) objects.stream().reduce((s1, s2) -> (s1 + "\n" + s2)).get());
                                    }
                                }
                            } catch (Exception e) {
                                return null;
                            }
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
