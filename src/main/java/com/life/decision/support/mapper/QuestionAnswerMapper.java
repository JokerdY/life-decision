package com.life.decision.support.mapper;

import com.life.decision.support.pojo.QuestionAnswer;
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
public interface QuestionAnswerMapper  {

    int deleteByPrimaryKey(String id);

    int insert(QuestionAnswer record);

    int insertSelective(QuestionAnswer record);

    QuestionAnswer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuestionAnswer record);

    int updateByPrimaryKey(QuestionAnswer record);
}
