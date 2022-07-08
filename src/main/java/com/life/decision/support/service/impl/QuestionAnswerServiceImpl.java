package com.life.decision.support.service.impl;

import cn.hutool.core.util.IdUtil;
import com.life.decision.support.mapper.QuestionAnswerMapper;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
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
    public void updateBatch(List<QuestionAnswer> list, QuestionnaireSubmitInformation submit) {
        LocalDateTime now = LocalDateTime.now();
        String submitId = submit.getId();
        for (QuestionAnswer answer : list) {
            answer.setSubmitId(submitId);
            if (answerMapper.selectByPrimaryKey(answer) != null) {
                answerMapper.updateByPrimaryKey(answer);
            } else {
                answer.setUserId(submit.getUserId());
                answer.setCreateDate(now);
                answer.setId(IdUtil.fastUUID());
                answer.setSubmitId(submitId);
                answer.setQuestionnaireId(submit.getQuestionnaireId());
                answerMapper.insert(answer);
            }
        }
    }

    @Override
    public List<QuestionAnswer> findList(QuestionAnswer questionAnswer) {
        return answerMapper.list(questionAnswer);
    }

    @Override
    public List<QuestionAnswer> listBySubmitId(List<String> list) {
        return answerMapper.listBySubmitId(list);
    }
}
