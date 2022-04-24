package com.life.decision.support.mapper;

import com.life.decision.support.dto.QuestionnaireInformationUserDto;
import com.life.decision.support.pojo.QuestionnaireInformation;
import org.apache.ibatis.annotations.Mapper;

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
public interface QuestionnaireInformationMapper {

    int deleteByPrimaryKey(String id);

    int insert(QuestionnaireInformation record);

    int insertSelective(QuestionnaireInformation record);

    QuestionnaireInformation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuestionnaireInformation record);

    int updateByPrimaryKey(QuestionnaireInformation record);

    List<QuestionnaireInformation> findList(QuestionnaireInformation questionnaireInformation);

    List<QuestionnaireInformationUserDto> findListInUser(QuestionnaireInformationUserDto dto);
}
