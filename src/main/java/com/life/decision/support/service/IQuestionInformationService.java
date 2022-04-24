package com.life.decision.support.service;

import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.pojo.QuestionAnswer;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
public interface IQuestionInformationService  {

    List<QuestionInformationDto> listById(String questionnaireId);

    List<QuestionInformationDto> findEditList(QuestionAnswer questionAnswer);
}
