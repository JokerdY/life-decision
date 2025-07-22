package com.life.decision.support.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.life.decision.support.bo.ContentAdvice;
import com.life.decision.support.bo.MassageVo;
import com.life.decision.support.bo.UrlAdvice;
import com.life.decision.support.mapper.ChineseMedicineMapper;
import com.life.decision.support.pojo.ChineseMedicine;
import com.life.decision.support.vo.ChineseMedicineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ChineseMedicineServiceImpl {
    @Autowired
    ChineseMedicineMapper mapper;

    public void saveOrUpdate(ChineseMedicine entity) {
        if (mapper.selectByEntity(entity) != null) {
            mapper.updateByPrimaryKeySelective(entity);
        } else {
            mapper.insert(entity);
        }
    }
    public void saveOrUpdateById(ChineseMedicine entity) {
        if (mapper.selectById(entity) != null) {
            mapper.updateByPrimaryKeySelective(entity);
        } else {
            mapper.insert(entity);
        }
    }

    public int delete(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int save(ChineseMedicine result) {
        return mapper.insert(result);
    }

    public int saveSelective(ChineseMedicine result) {
        return mapper.insertSelective(result);
    }

    public ChineseMedicine findByEntity(ChineseMedicine result) {
        return mapper.selectByEntity(result);
    }

    public List<ChineseMedicine> listByEntity(ChineseMedicine result) {
        return mapper.listByEntity(result);
    }

    public ContentAdvice getAdvice(String userId) {
        ChineseMedicine dto = new ChineseMedicine();
        dto.setUserId(userId);
        ChineseMedicineVo vo = getVo(dto);
        if (vo == null || vo.getMassageList().isEmpty()) {
            return new ContentAdvice(null, null);
        } else {
            return vo.getMassageList()
                    .stream()
                    .map(MassageVo::getAcupuncturePointsList)
                    .flatMap(Collection::stream)
                    .map(url -> new ContentAdvice(url.getName(), url.getName()))
                    .findAny()
                    .orElse(new ContentAdvice(null, null));
        }
    }

    public ChineseMedicineVo getVo(ChineseMedicine dto) {
        ChineseMedicineVo vo = new ChineseMedicineVo();
        ChineseMedicine byEntity = findByEntity(dto);
        if (byEntity != null) {
            vo.setId(byEntity.getId());
            JSONObject xuewei = JSONUtil.parseObj(byEntity.getAcupressure());
            List<MassageVo> list = new ArrayList<>();
            xuewei.forEach((k, v) -> {
                MassageVo e = new MassageVo();
                e.setName(k);
                List<UrlAdvice> apl = new ArrayList<>();
                JSONArray arr = JSONUtil.parseArray(v);
                for (Object o : arr) {
                    JSONObject jsonObject = JSONUtil.parseObj(o);
                    if (!jsonObject.isEmpty()) {
                        for (String key : jsonObject.keySet()) {
                            apl.add(new UrlAdvice(key, "", jsonObject.getStr(key)));
                        }
                    }
                }
                e.setAcupuncturePointsList(apl);
                list.add(e);
            });
            vo.setMassageList(list);
            List<UrlAdvice> urls = new ArrayList<>();
            JSONArray musics = JSONUtil.parseArray(byEntity.getFiveElementsMusic());
            for (Object music : musics) {
                JSONObject jsonObject = JSONUtil.parseObj(music);
                if (!jsonObject.isEmpty()) {
                    for (String key : jsonObject.keySet()) {
                        urls.add(new UrlAdvice(key, "", jsonObject.getStr(key)));
                    }
                }
            }
            vo.setMusicList(urls);
        } else {
            vo.setMusicList(new ArrayList<>());
            vo.setMassageList(new ArrayList<>());
            vo.setId(IdUtil.fastSimpleUUID());
        }
        return vo;
    }

    public ChineseMedicineVo getVoBySubmitId(String submitId) {
        ChineseMedicine byEntity = mapper.selectBySubmitId(submitId);
        return getVo(byEntity);
    }

}
