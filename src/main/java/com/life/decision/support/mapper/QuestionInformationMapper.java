package com.life.decision.support.mapper;

import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.pojo.QuestionInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    int insert(QuestionInformation record);

    int insertSelective(QuestionInformation record);

    int updateByPrimaryKeySelective(QuestionInformation record);

    int updateByPrimaryKeyWithBLOBs(QuestionInformation record);

    int updateByPrimaryKey(QuestionInformation record);

    List<QuestionInformationDto> listById(@Param("questionnaireId") String questionnaireId);
}
