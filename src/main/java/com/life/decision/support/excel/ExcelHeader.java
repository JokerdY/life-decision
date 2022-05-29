package com.life.decision.support.excel;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.life.decision.support.dto.QuestionInformationDto;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.ArrayList;
import java.util.List;

public class ExcelHeader {
    List<List<String>> head;
    HorizontalCellStyleStrategy horizontalCellStyleStrategy;

    public static class Builder {
        public ExcelHeader build(List<QuestionInformationDto> questionInformationDtoList) {
            ExcelHeader excelHeader = new ExcelHeader();
            excelHeader.setHead(getExcelHead(questionInformationDtoList));
            excelHeader.setHorizontalCellStyleStrategy(getHeaderStyle());
            return excelHeader;
        }

        private HorizontalCellStyleStrategy getHeaderStyle() {
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontHeightInPoints((short) 11);
            headWriteFont.setFontName("等线");
            headWriteCellStyle.setWriteFont(headWriteFont);
            headWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            return new HorizontalCellStyleStrategy(headWriteCellStyle, new WriteCellStyle());
        }

        private List<List<String>> getExcelHead(List<QuestionInformationDto> questionInformationDtoList) {
            List<List<String>> list = new ArrayList<>();
            getFixedHead(list);
            for (QuestionInformationDto dto : questionInformationDtoList) {
                getHeaderInfo(list, dto);
                if (CollUtil.isNotEmpty(dto.getChild())) {
                    for (QuestionInformationDto childDto : dto.getChild()) {
                        getHeaderInfo(list, childDto);
                    }
                }
            }
            return list;
        }

        private void getFixedHead(List<List<String>> list) {
            list.add(new ArrayList<String>() {{
                add("系统编号");
            }});
            list.add(new ArrayList<String>() {{
                add("账号");
            }});
            list.add(new ArrayList<String>() {{
                add("姓名");
            }});
            list.add(new ArrayList<String>() {{
                add("性别");
            }});
            list.add(new ArrayList<String>() {{
                add("年龄");
            }});
            list.add(new ArrayList<String>() {{
                add("婚姻状况");
            }});
            list.add(new ArrayList<String>() {{
                add("文化程度");
            }});
            list.add(new ArrayList<String>() {{
                add("职业");
            }});
            list.add(new ArrayList<String>() {{
                add("家庭人均月收入");
            }});
        }

        private void getHeaderInfo(List<List<String>> list, QuestionInformationDto dto) {
            StringBuilder tempHead = new StringBuilder()
                    .append(dto.getQuestionName());
            dto.getOptionInformationList()
                    .stream()
                    .map(s -> s.getOptionSort() + ":" + s.getOptionValue().replaceAll("\\{\\}", "多少"))
                    .reduce((s1, s2) -> s1 + "," + s2)
                    .ifPresent(s -> tempHead.append("--").append(s));
            list.add(new ArrayList<String>() {{
                add(tempHead.toString());
            }});
        }
    }

    private ExcelHeader() {
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
        return horizontalCellStyleStrategy;
    }

    public void setHorizontalCellStyleStrategy(HorizontalCellStyleStrategy horizontalCellStyleStrategy) {
        this.horizontalCellStyleStrategy = horizontalCellStyleStrategy;
    }
}