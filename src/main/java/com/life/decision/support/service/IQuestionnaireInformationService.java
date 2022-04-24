package com.life.decision.support.service;

import com.life.decision.support.dto.QuestionnaireInformationUserDto;
import com.life.decision.support.pojo.QuestionnaireInformation;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
public interface IQuestionnaireInformationService {

    List<QuestionnaireInformation> findList(QuestionnaireInformation questionnaireInformation);

    List<QuestionnaireInformationUserDto> findListInUser(QuestionnaireInformationUserDto dto);
}
