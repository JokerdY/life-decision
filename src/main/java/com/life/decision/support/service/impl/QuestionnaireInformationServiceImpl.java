package com.life.decision.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.life.decision.support.dto.QuestionnaireInformationUserDto;
import com.life.decision.support.mapper.QuestionnaireInformationMapper;
import com.life.decision.support.pojo.QuestionnaireInformation;
import com.life.decision.support.service.IQuestionnaireInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Service
public class QuestionnaireInformationServiceImpl implements IQuestionnaireInformationService {

    @Autowired
    QuestionnaireInformationMapper questionnaireInformationMapper;

    @Override
    public List<QuestionnaireInformation> findList(QuestionnaireInformation questionnaireInformation) {
        PageHelper.startPage(questionnaireInformation);
        return questionnaireInformationMapper.findList(questionnaireInformation);
    }

    @Override
    public QuestionnaireInformation selectByPrimaryKey(String questionnaireId) {
        return questionnaireInformationMapper.selectByPrimaryKey(questionnaireId);
    }

    /**
     * 问卷组信息
     * @param dto
     * @return
     */
    @Override
    public List<QuestionnaireInformationUserDto> findListInUser(QuestionnaireInformationUserDto dto) {
        PageHelper.startPage(dto);
        List<QuestionnaireInformationUserDto> listInUser = questionnaireInformationMapper.findListInUser(dto);
        // 获取
        listInUser
                .stream()
                .map(QuestionnaireInformationUserDto::getSubmitCount)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .ifPresent(max -> {
                    for (QuestionnaireInformationUserDto userDto : listInUser) {
                        // 如果提交次数一样 则认为在当前问卷组已提交 设置submitEnabled
                        // 否则submitEnabled置空
                        if (userDto.getSubmitCount() != null && max.equals(userDto.getSubmitCount())) {
                            userDto.setSubmitEnabled(userDto.getFillDate() != null);
                        } else {
                            userDto.setSubmitEnabled(false);
                            userDto.setFillDate(null);
                        }
                    }
                });
        return listInUser;
    }
}
