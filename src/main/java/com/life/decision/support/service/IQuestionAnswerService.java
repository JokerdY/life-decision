package com.life.decision.support.service;

import com.life.decision.support.dto.AnswerDto;
import com.life.decision.support.dto.DataAnalysisDto;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
public interface IQuestionAnswerService {

    Integer saveBatch(List<QuestionAnswer> list, String userId, String questionnaireId, LocalDateTime now, String submitId);

    List<AnswerDto> selectSingleAnswerByUser(String questionId, DataAnalysisDto dto);

    List<AnswerDto> selectMultipleAnswerByUser(String questionId, DataAnalysisDto dto);

    void updateBatch(List<QuestionAnswer> list, QuestionnaireSubmitInformation submit);

    List<QuestionAnswer> findList(QuestionAnswer questionAnswer);

    List<QuestionAnswer> listBySubmitId(List<String> list);
}
