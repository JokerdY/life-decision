package com.life.decision.support.mapper;

import com.life.decision.support.pojo.QuestionInformation;
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
public interface QuestionInformationMapper {

    int deleteByPrimaryKey(String id);

    int insert(QuestionInformation record);

    int insertSelective(QuestionInformation record);

    QuestionInformation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuestionInformation record);

    int updateByPrimaryKeyWithBLOBs(QuestionInformation record);

    int updateByPrimaryKey(QuestionInformation record);
}
