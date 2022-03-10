package com.life.decision.support.mapper;

import com.life.decision.support.pojo.OptionInformation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Mapper
public interface OptionInformationMapper {

    int deleteByPrimaryKey(String id);

    int insert(OptionInformation record);

    int insertSelective(OptionInformation record);

    OptionInformation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OptionInformation record);

    int updateByPrimaryKey(OptionInformation record);
}
