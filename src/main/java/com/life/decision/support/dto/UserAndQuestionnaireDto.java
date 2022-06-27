package com.life.decision.support.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserAndQuestionnaireDto {
    private UserInformationDto user;
    private List<SysDictDto> list;
}
