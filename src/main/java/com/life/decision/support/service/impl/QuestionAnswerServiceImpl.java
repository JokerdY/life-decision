package com.life.decision.support.service.impl;

import cn.hutool.core.util.IdUtil;
import com.life.decision.support.mapper.QuestionAnswerMapper;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.service.IQuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Service
public class QuestionAnswerServiceImpl implements IQuestionAnswerService {

    @Autowired
    QuestionAnswerMapper answerMapper;

    @Override
    public Integer saveBatch(List<QuestionAnswer> list, String userId, String questionnaireId, LocalDateTime now, String submitId) {
        list.forEach(answer -> {
            answer.setQuestionnaireId(questionnaireId);
            answer.setUserId(userId);
            answer.setCreateDate(now);
            answer.setId(IdUtil.fastUUID());
            answer.setSubmitId(submitId);
        });
        return answerMapper.insertBatch(list);
    }

    @Override
    public void updateBatch(List<QuestionAnswer> list) {
        for (QuestionAnswer questionAnswer : list) {
            answerMapper.updateByPrimaryKey(questionAnswer);
        }
    }

    @Override
    public List<QuestionAnswer> findList(QuestionAnswer questionAnswer) {
        return answerMapper.list(questionAnswer);
    }
}
