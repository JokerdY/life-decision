package com.life.decision.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.life.decision.support.dto.QuestionnaireInformationUserDto;
import com.life.decision.support.mapper.QuestionnaireGroupInformationMapper;
import com.life.decision.support.mapper.QuestionnaireInformationMapper;
import com.life.decision.support.pojo.QuestionnaireGroupInformation;
import com.life.decision.support.pojo.QuestionnaireInformation;
import com.life.decision.support.service.IQuestionnaireInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    QuestionnaireGroupInformationMapper questionnaireGroupInformationMapper;

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
     *
     * @param dto
     * @return
     */
    @Override
    public List<QuestionnaireInformationUserDto> findListInUser(QuestionnaireInformationUserDto dto) {
        PageHelper.startPage(dto);
        // 找到问卷信息
        List<QuestionnaireInformationUserDto> listInUser = questionnaireInformationMapper.findListInUser(dto);
        QuestionnaireGroupInformation groupInfo = questionnaireGroupInformationMapper.getByUserId(dto.getUserId());
        List<QuestionnaireGroupInformation> groupList = new ArrayList<>();
        if (groupInfo != null && groupInfo.getGroupId() != null) {
            groupList = questionnaireGroupInformationMapper.findList(groupInfo);
        }
        // 查询是否存在最大的groupId 且问卷数量
        for (QuestionnaireInformationUserDto questionnaire : listInUser) {
            Optional<QuestionnaireGroupInformation> groupInformation = groupList.stream().filter(group -> questionnaire.getId().equals(group.getQuestionnaireId())).findAny();
            if (groupInformation.isPresent()) {
                QuestionnaireGroupInformation g = groupInformation.get();
                questionnaire.setFillDate(Date.from(g.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant()));
                questionnaire.setSubmitId(g.getSubmitId());
                questionnaire.setSubmitEnabled(true);
            } else {
                questionnaire.setSubmitEnabled(false);
            }
        }
        return listInUser;
    }
}
