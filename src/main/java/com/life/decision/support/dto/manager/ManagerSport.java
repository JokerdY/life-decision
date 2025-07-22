package com.life.decision.support.dto.manager;

import com.life.decision.support.bo.ContentAdvice;
import lombok.Data;

import java.util.List;

@Data
public class ManagerSport {

    private String id;

    private String nowDay;

    private String userId;

    private List<String> dateRecord;

    private List<ContentAdvice> healthEducation;

    private List<ManagerSportChild> contents;

}
