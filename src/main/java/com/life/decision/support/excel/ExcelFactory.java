package com.life.decision.support.excel;

import com.life.decision.support.dto.QuestionInformationDto;
import com.life.decision.support.service.IQuestionAnswerService;
import com.life.decision.support.service.IQuestionnaireSubmitInformationService;
import com.life.decision.support.service.IUserInformationService;

import java.util.List;

public class ExcelFactory {
    private ExcelHeader header;
    private ExcelData data;

    public static class Builder {
        public ExcelFactory build(List<QuestionInformationDto> questionInformationDtoList,
                                  List<String> groupIdsByDate,
                                  IQuestionnaireSubmitInformationService submitInformationService,
                                  IQuestionAnswerService answerService,
                                  IUserInformationService userInformationService) {
            ExcelFactory excelFactory = new ExcelFactory();
            excelFactory.setHeader(new ExcelHeader
                    .Builder()
                    .build(questionInformationDtoList));
            excelFactory.setData(new ExcelData
                    .Builder()
                    .groupIdsByDate(groupIdsByDate)
                    .IQuestionAnswerService(answerService)
                    .userInformationService(userInformationService)
                    .submitInformationService(submitInformationService)
                    .questionInformationDtoList(questionInformationDtoList)
                    .build());
            return excelFactory;
        }

    }

    private ExcelFactory() {
    }

    public ExcelHeader getHeader() {
        return header;
    }

    public void setHeader(ExcelHeader header) {
        this.header = header;
    }

    public ExcelData getData() {
        return data;
    }

    public void setData(ExcelData data) {
        this.data = data;
    }
}
