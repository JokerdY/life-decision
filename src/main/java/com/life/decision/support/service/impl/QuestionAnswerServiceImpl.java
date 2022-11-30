package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.life.decision.support.dto.AnswerDto;
import com.life.decision.support.dto.DataAnalysisDto;
import com.life.decision.support.mapper.OptionInformationMapper;
import com.life.decision.support.mapper.QuestionAnswerMapper;
import com.life.decision.support.pojo.OptionInformation;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.service.IQuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    OptionInformationMapper optionInformationMapper;

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
    public List<AnswerDto> selectSingleAnswerByUser(String questionId, DataAnalysisDto dto) {
        return answerMapper.selectAnswerByUser(questionId, dto);
    }

    @Override
    public List<AnswerDto> selectMultipleAnswerByUser(String questionId, DataAnalysisDto dto) {
        List<AnswerDto> answerDtos = answerMapper.selectAnswerByUser(questionId, dto);
        List<AnswerDto> result = new ArrayList<>();
        Map<String, String> optionIdMapping = new HashMap<>();
        for (AnswerDto answerDto : answerDtos) {
            String[] optionList = answerDto.getOptionId().split(",");
            for (String optionId : optionList) {
                AnswerDto temp = new AnswerDto();
                BeanUtil.copyProperties(answerDto, temp);
                String optionValue;
                if (optionIdMapping.containsKey(optionId)) {
                    optionValue = optionIdMapping.get(optionId);
                } else {
                    OptionInformation optionInformation = optionInformationMapper.selectByPrimaryKey(optionId);
                    optionValue = optionInformation.getOptionValue();
                    optionIdMapping.put(optionId, optionValue);
                }
                temp.setOptionValue(optionValue);
                result.add(temp);
            }
        }
        return result;
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
