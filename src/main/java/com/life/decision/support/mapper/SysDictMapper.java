package com.life.decision.support.mapper;

import com.life.decision.support.dto.SysDictDto;
import com.life.decision.support.pojo.SysDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Mapper
public interface SysDictMapper {

    List<SysDictDto> selectByKey(SysDict sysDict);

}
