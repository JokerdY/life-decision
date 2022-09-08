package com.life.decision.support.service.impl;

import com.life.decision.support.dto.SysDictDto;
import com.life.decision.support.mapper.SysDictMapper;
import com.life.decision.support.pojo.SysDict;
import com.life.decision.support.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Service
public class SysDictServiceImpl implements ISysDictService {

    @Autowired
    SysDictMapper sysDictMapper;

    @Override
    public List<SysDictDto> dictList(String columnName, String tableName) {
        SysDict sysDict = new SysDict();
        sysDict.setColumnName(columnName);
        sysDict.setTableName(tableName);
        return sysDictMapper.selectByKey(sysDict);
    }

    @Override
    public List<SysDictDto> dictList(String columnName) {
        return dictList(columnName, null);
    }

    @Override
    public SysDictDto getDict(String columnName, String columnValue) {
        List<SysDictDto> sysDictDtos = dictList(columnName);
        for (SysDictDto s : sysDictDtos) {
            if (s.getValue().equals(columnValue)) {
                return s;
            }
        }
        return new SysDictDto();
    }
}
