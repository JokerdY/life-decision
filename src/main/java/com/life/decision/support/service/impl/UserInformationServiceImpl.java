package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.life.decision.support.dto.UserInHomeVo;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.mapper.UserInformationMapper;
import com.life.decision.support.pojo.PassWordChangeDto;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.IUserInformationService;
import com.life.decision.support.service.QuestionnaireMsgQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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
public class UserInformationServiceImpl implements IUserInformationService {

    @Autowired
    QuestionnaireMsgQueryService questionnaireMsgQueryService;
    @Autowired
    private UserInformationMapper userInformationMapper;

    @Override
    public List<UserInformationDto> findList(UserInformationDto userInformationDto) {
        UserInformation temp = BeanUtil.copyProperties(userInformationDto, UserInformation.class);
        PageHelper.startPage(userInformationDto);
        return userInformationMapper.findList(temp);
    }

    @Override
    public List<UserInformationDto> findAllList(UserInformationDto userInformationDto) {
        return userInformationMapper.findList(BeanUtil.copyProperties(userInformationDto, UserInformation.class));
    }

    @Override
    public boolean isExist(UserInformation userInformation) {
        UserInformation temp = new UserInformation();
        temp.setTelphoneNum(userInformation.getTelphoneNum());
        return userInformationMapper.getUser(temp) != null;
    }

    @Override
    public boolean insertUser(UserInformation userInformation) {
        if (StrUtil.isBlank(userInformation.getId())) {
            userInformation.setId(IdUtil.fastUUID());
        }
        return userInformationMapper.insert(userInformation) > 0;
    }

    @Override
    public boolean verifyIdentified(UserInformation userInformation) {
        return userInformationMapper.verifyIdentified(userInformation) == 1;
    }

    @Override
    public boolean resetPassword(UserInformation userInformation, int tailNumberDigits) {
        UserInformation temp = new UserInformation();
        temp.setTelphoneNum(userInformation.getTelphoneNum());
        String telStr = userInformation.getTelphoneNum();
        temp.setPassword(telStr.substring(telStr.length() - tailNumberDigits));
        return userInformationMapper.update(temp) > 0;
    }

    @Override
    public UserInHomeVo getUserMsg(UserInformation userInformation) {
        UserInformationDto user = userInformationMapper.getUser(userInformation);
        if (user != null) {
            String userId = user.getId();
            user.setPassword(null);

            UserInHomeVo userInHomeVo = new UserInHomeVo();
            BeanUtil.copyProperties(user, userInHomeVo);
            QuestionAnswer lastAnswer = questionnaireMsgQueryService.getLastAnswer(userId, "175", 1);
            // 一次问卷没有填过
            if (lastAnswer == null) {
                userInHomeVo.setBloodPressure("暂无血压数据");
                userInHomeVo.setFillInTheDate("请填写问卷");
                userInHomeVo.setBloodPressureStr("---");
                userInHomeVo.setBodyFatPercentage("0.0%");
            } else {
                String fillInTheDate = lastAnswer.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
                userInHomeVo.setFillInTheDate(fillInTheDate);
                String bloodPressure = questionnaireMsgQueryService.getLatestOptionValue(userId, "175");
                userInHomeVo.setBloodPressure(bloodPressure);

                String[] highAndLowPressure = bloodPressure.split("/");

                String height = questionnaireMsgQueryService.getLatestOptionValue(userId, "172");
                String weight = questionnaireMsgQueryService.getLatestOptionValue(userId, "173");
                double bmiDouble = getBmi(height, weight);
                String BMI = String.format("%.1f", bmiDouble);
                if (questionnaireMsgQueryService.getSecondLastOption(userId, "175") != null) {
                    // 填写了两次以上
                    String secondHeight = questionnaireMsgQueryService.getSecondLastOptionValue(userId, "172");
                    String secondWeight = questionnaireMsgQueryService.getSecondLastOptionValue(userId, "173");
                    double secondBmiDouble = getBmi(secondHeight, secondWeight);
                    getDiff(userInHomeVo, weight, bmiDouble, secondWeight, secondBmiDouble);
                } else {
                    // 填写了一次
                    getDiff(userInHomeVo, weight, bmiDouble, userInHomeVo.getWeight(), Double.parseDouble(userInHomeVo.getBMI()));
                }
                double bodyFatPercentage = 1.2 * bmiDouble + 0.23 * Integer.parseInt(userInHomeVo.getAge()) - 5.4 - 10.8 * userInHomeVo.getSex() * 100;
                userInHomeVo.setBodyFatPercentage(String.format("%.1f", bodyFatPercentage) + "%");
                userInHomeVo.setWeight(weight);
                userInHomeVo.setBMI(BMI);
            }
            return userInHomeVo;
        }
        return null;
    }

    private void getDiff(UserInHomeVo userInHomeVo, String weight, double bmiDouble, String secondWeight, double secondBmiDouble) {
        userInHomeVo.setWeightDiff(String.format("%.1f", Double.parseDouble(weight) - Double.parseDouble(secondWeight)));
        userInHomeVo.setBMIDiff(String.format("%.1f", bmiDouble - secondBmiDouble));
    }

    private double getBmi(String secondHeight, String secondWeight) {
        return Double.parseDouble(secondWeight) / (Math.pow(Double.parseDouble(secondHeight) / 100, 2));
    }

    @Override
    public UserInformationDto getAdminUser(UserInformation userInformation) {
        return userInformationMapper.getUser(userInformation);
    }

    @Override
    public boolean changePassword(PassWordChangeDto userInformation) {
        return userInformationMapper.changePassword(userInformation) == 1;
    }

    @Override
    public boolean editPersonalInformation(UserInformation userInformation) {
        UserInformation temp = new UserInformation();
        BeanUtil.copyProperties(userInformation, temp);
        temp.setPassword(null);
        return userInformationMapper.update(temp) == 1;
    }
}
