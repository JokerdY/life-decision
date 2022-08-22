package com.life.decision.support.dto.manager;

import com.life.decision.support.vo.ChineseMedicineVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagerMedicine extends ChineseMedicineVo {
    private List<ManagerPoint> points;
}
