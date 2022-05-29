package com.life.decision.support.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.QuestionnaireSubmitInformation;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.service.IUserInformationService;

import java.util.*;
import java.util.stream.Collectors;

public class ExcelData {

    private List<List<String>> realData;

    public static class Builder {
        private List<String> groupIdsByDate;
        private IQuestionnaireSubmitInformationService submitInformationService;
        private List<QuestionInformationDto> questionInformationDtoList;
        private IQuestionAnswerService answerService;
        private IUserInformationService userInformationService;

        public ExcelData build() {
            ExcelData data = new ExcelData();
            Map<String, UserInformationDto> userMap = new HashMap<>();
            List<List<String>> realData = new ArrayList<>();
            for (String groupId : groupIdsByDate) {
                List<String> lineData = new ArrayList<>();
                // 根据groupId 找到对应的提交问卷
                List<QuestionnaireSubmitInformation> submitInfos = submitInformationService.listIdByGroupIds(new ArrayList<String>() {{
                    add(groupId);
                }});
                if (CollUtil.isNotEmpty(submitInfos)) {
                    getFixData(userMap, lineData, submitInfos.get(0));
                    // 根据当前组的submitId查问卷组的结果
                    List<QuestionAnswer> questionAnswers = answerService.listBySubmitId(submitInfos
                            .stream()
                            .map(QuestionnaireSubmitInformation::getId)
                            .collect(Collectors.toList()));
                    for (QuestionInformationDto dto : questionInformationDtoList) {
                        // 遍历问卷问题 如果有答案 则add值 否则 add空
                        getDataInfo(lineData, questionAnswers, dto);
                        if (CollUtil.isNotEmpty(dto.getChild())) {
                            for (QuestionInformationDto childDto : dto.getChild()) {
                                getDataInfo(lineData, questionAnswers, childDto);
                            }
                        }
                    }
                }
                realData.add(lineData);
            }
            data.setRealData(realData);
            return data;
        }

        private void getDataInfo(List<String> lineData, List<QuestionAnswer> questionAnswers, QuestionInformationDto dto) {
            Optional<QuestionAnswer> ansOptional = questionAnswers
                    .stream()
                    .filter(s -> s.getQuestionId().equals(dto.getId()) && s.getQuestionnaireId().equals(dto.getQuestionnaireId()))
                    .findAny();
            if (ansOptional.isPresent()) {
                QuestionAnswer questionAnswer = ansOptional.get();
                if (StrUtil.isBlank(questionAnswer.getComment())) {
                    lineData.add(questionAnswer.getOptionId());
                } else {
                    lineData.add(questionAnswer.getOptionId() + "." + questionAnswer.getComment());
                }
            } else {
                lineData.add("");
            }
        }

        private void getFixData(Map<String, UserInformationDto> userMap, List<String> lineData, QuestionnaireSubmitInformation submitInfo) {
            String userId = submitInfo.getUserId();
            UserInformationDto userMsg;
            if (userMap.containsKey(userId)) {
                userMsg = userMap.get(userId);
            } else {
                UserInformation userInformation = new UserInformation();
                userInformation.setId(userId);
                userMsg = userInformationService.getUserMsg(userInformation);
                userMap.put(userId, userMsg);
            }
            lineData.add(userId);
            lineData.add(userMsg.getTelphoneNum());
            lineData.add(userMsg.getUserName());
            lineData.add(userMsg.getSexDto() + "");
            lineData.add(userMsg.getAge());
            lineData.add(userMsg.getMaritalDto() + "");
            lineData.add(userMsg.getOccupationDto() + "");
            lineData.add(userMsg.getEducationalLevelDto() + "");
            lineData.add(userMsg.getHouseholdIncomeDto() + "");
        }

        public Builder groupIdsByDate(List<String> groupIdsByDate) {
            this.groupIdsByDate = groupIdsByDate;
            return this;
        }

        public Builder submitInformationService(IQuestionnaireSubmitInformationService submitInformationService) {
            this.submitInformationService = submitInformationService;
            return this;
        }

        public Builder questionInformationDtoList(List<QuestionInformationDto> questionInformationDtoList) {
            this.questionInformationDtoList = questionInformationDtoList;
            return this;
        }

        public Builder IQuestionAnswerService(IQuestionAnswerService answerService) {
            this.answerService = answerService;
            return this;
        }

        public Builder userInformationService(IUserInformationService userInformationService) {
            this.userInformationService = userInformationService;
            return this;
        }
    }

    public List<List<String>> getRealData() {
        return realData;
    }

    public void setRealData(List<List<String>> realData) {
        this.realData = realData;
    }

    private ExcelData() {
    }

}
