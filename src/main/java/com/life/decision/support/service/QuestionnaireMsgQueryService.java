package com.life.decision.support.service;

import com.life.decision.support.dto.DataAnalysisDto;
import com.life.decision.support.mapper.OptionInformationMapper;
import com.life.decision.support.mapper.QuestionAnswerMapper;
import com.life.decision.support.pojo.OptionInformation;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.service.impl.QuestionAnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class QuestionnaireMsgQueryService {

    @Autowired
    QuestionAnswerMapper answerMapper;
    @Autowired
    OptionInformationMapper optionInformationMapper;
    @Autowired
    QuestionAnswerServiceImpl answerService;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    public List<QuestionAnswer> getAnswerListByDate(DataAnalysisDto dto, String questionId) {
        Predicate<LocalDate> dateFilter = getDateFilter(dto);
        QuestionAnswer temp = new QuestionAnswer();
        temp.setQuestionId(questionId);
        temp.setUserId(dto.getUserId());
        return answerService.findList(temp)
                .stream()
                .filter(s -> dateFilter.test(s.getCreateDate().toLocalDate()))
                .collect(Collectors.toList());
    }

    private Predicate<LocalDate> getDateFilter(DataAnalysisDto dto) {
        LocalDate startDate = LocalDate.parse(dto.getStartDate(), FORMATTER);
        LocalDate endDate = LocalDate.parse(dto.getEndDate(), FORMATTER);
        Predicate<LocalDate> dateFilter = date -> date.compareTo(startDate) > 0 && date.compareTo(endDate) < 0;
        return dateFilter;
    }

}
