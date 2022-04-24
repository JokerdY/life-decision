package com.life.decision.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.life.decision.support.dto.QuestionnaireInformationUserDto;
import com.life.decision.support.mapper.QuestionnaireInformationMapper;
import com.life.decision.support.pojo.QuestionnaireInformation;
import com.life.decision.support.service.IQuestionnaireInformationService;
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
public class QuestionnaireInformationServiceImpl implements IQuestionnaireInformationService {

    @Autowired
    QuestionnaireInformationMapper questionnaireInformationMapper;

    @Override
    public List<QuestionnaireInformation> findList(QuestionnaireInformation questionnaireInformation) {
        PageHelper.startPage(questionnaireInformation);
        return questionnaireInformationMapper.findList(questionnaireInformation);
    }

    @Override
    public List<QuestionnaireInformationUserDto> findListInUser(QuestionnaireInformationUserDto dto) {
        PageHelper.startPage(dto);
        List<QuestionnaireInformationUserDto> listInUser = questionnaireInformationMapper.findListInUser(dto);
        listInUser.forEach(s -> s.setSubmitEnabled(s.getCreateTime() != null));
        return listInUser;
    }
}
