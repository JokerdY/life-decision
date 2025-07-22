package com.life.decision.support.service;

import com.life.decision.support.dto.SysDictDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
public interface ISysDictService {

    List<SysDictDto> dictList(String columnName, String tableName);

    List<SysDictDto> dictList(String columnName);

    SysDictDto getDict(String columnName, String columnValue);

}
