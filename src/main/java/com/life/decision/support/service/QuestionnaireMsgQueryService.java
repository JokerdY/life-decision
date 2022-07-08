package com.life.decision.support.service;

import com.life.decision.support.mapper.OptionInformationMapper;
import com.life.decision.support.mapper.QuestionAnswerMapper;
import com.life.decision.support.pojo.OptionInformation;
import com.life.decision.support.pojo.QuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireMsgQueryService {

    @Autowired
    QuestionAnswerMapper answerMapper;
    @Autowired
    OptionInformationMapper optionInformationMapper;

    // 查询用户最新一次的某个问题Id对应的数据
    public OptionInformation getLatestOption(String userId, String questionId) {
        return getLastOption(userId, questionId, 1);
    }

    // 查询倒数第二次的数据
    public OptionInformation getSecondLastOption(String userId, String questionId) {
        return getLastOption(userId, questionId, 2);
    }

    // 查询用户最新一次的某个问题Id对应的数据
    public String getLatestOptionValue(String userId, String questionId) {
        OptionInformation lastAnswer = getLatestOption(userId, questionId);
        if (lastAnswer != null) {
            return lastAnswer.getOptionValue();
        }
        return null;
    }

    // 查询倒数第二次的数据
    public String getSecondLastOptionValue(String userId, String questionId) {
        OptionInformation lastAnswer = getSecondLastOption(userId, questionId);
        if (lastAnswer != null) {
            return lastAnswer.getOptionValue();
        }
        return null;
    }

    public OptionInformation getLastOption(String userId, String questionId, int latestNum) {
        QuestionAnswer ans = getLastAnswer(userId, questionId, latestNum);
        if (ans != null) {
            return optionInformationMapper.selectByPrimaryKey(ans.getOptionId());
        }
        return null;
    }

    public QuestionAnswer getLastAnswer(String userId, String questionId, int latestNum) {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setUserId(userId);
        questionAnswer.setQuestionId(questionId);
        List<QuestionAnswer> list = answerMapper.list(questionAnswer);
        list.sort((s1, s2) -> s2.getCreateDate().compareTo(s1.getCreateDate()));
        if (list.size() >= latestNum) {
            return list.get(latestNum - 1);
        }
        return null;
    }
}
