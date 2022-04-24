package com.life.decision.support.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.mapper.QuestionInformationMapper;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class QuestionInformationServiceImpl implements IQuestionInformationService {

    @Autowired
    private QuestionInformationMapper informationMapper;
    @Autowired
    private IQuestionAnswerService questionAnswerService;

    @Override
    public List<QuestionInformationDto> listById(String questionnaireId) {
        return informationMapper.listById(questionnaireId);
    }

    @Override
    public List<QuestionInformationDto> findEditList(QuestionAnswer questionAnswer) {
        List<QuestionInformationDto> list = informationMapper.listById(questionAnswer.getQuestionnaireId());
        List<QuestionAnswer> answerList = questionAnswerService.findList(questionAnswer);
        for (QuestionInformationDto questionInformationDto : list) {
            uploadAnswer(answerList, questionInformationDto);
            if (CollUtil.isEmpty(questionInformationDto.getChild())) {
                for (QuestionInformationDto informationDto : questionInformationDto.getChild()) {
                    uploadAnswer(answerList, informationDto);
                }
            }
        }
        return list;
    }

    private void uploadAnswer(List<QuestionAnswer> answerList, QuestionInformationDto dto) {
        answerList.stream()
                .filter(s -> s.getQuestionId().equals(dto.getId()))
                .findAny()
                .ifPresent(dto::setQuestionAnswer);
    }
}
