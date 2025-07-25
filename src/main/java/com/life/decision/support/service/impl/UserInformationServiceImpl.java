package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.life.decision.support.vo.UserInHomeVo;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.mapper.UserInformationMapper;
import com.life.decision.support.pojo.PassWordChangeDto;
import com.life.decision.support.pojo.QuestionAnswer;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.IUserInformationService;
import com.life.decision.support.service.QuestionnaireMsgQueryService;
import com.life.decision.support.vo.DataCountByMouthVo;
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
    @Autowired
    private SportsResultServiceImpl sportsResultService;
    @Autowired
    private PsychologyResultServiceImpl psychologyResultService;
    @Autowired
    private RecipeResultServiceImpl recipeResultService;
    @Autowired
    private ChineseMedicineServiceImpl chineseMedicineService;

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
                userInHomeVo.setBloodPressure("暂无");
                userInHomeVo.setFillInTheDate("请填写问卷");
                userInHomeVo.setBloodPressureStr("---");
                userInHomeVo.setBodyFatPercentage("0.0%");
            } else {
                String fillInTheDate = lastAnswer.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
                userInHomeVo.setFillInTheDate(fillInTheDate);
                userInHomeVo.setBloodPressure(lastAnswer.getComment());

                String[] highAndLowPressure = lastAnswer.getComment().split("/");
                userInHomeVo.setBloodPressureStr(getStandardBloodPressure(highAndLowPressure));
                String height = questionnaireMsgQueryService.getLastAnswer(userId, "172", 1).getComment();
                String weight = questionnaireMsgQueryService.getLastAnswer(userId, "173", 1).getComment();
                double bmiDouble = getBmi(height, weight);
                String BMI = String.format("%.1f", bmiDouble);
                if (questionnaireMsgQueryService.getSecondLastOption(userId, "175") != null) {
                    // 填写了两次以上
                    String secondHeight = questionnaireMsgQueryService.getLastAnswer(userId, "172", 2).getComment();
                    String secondWeight = questionnaireMsgQueryService.getLastAnswer(userId, "173", 2).getComment();
                    double secondBmiDouble = getBmi(secondHeight, secondWeight);
                    getDiff(userInHomeVo, weight, bmiDouble, secondWeight, secondBmiDouble);
                } else {
                    // 填写了一次
                    getDiff(userInHomeVo, weight, bmiDouble, userInHomeVo.getWeight(), Double.parseDouble(userInHomeVo.getBMI()));
                }
                double bodyFatPercentage = 1.2 * bmiDouble + 0.23 * Integer.parseInt(userInHomeVo.getAge()) - 5.4 - 10.8 * userInHomeVo.getSex();
                userInHomeVo.setBodyFatPercentage(String.format("%.1f", bodyFatPercentage) + "%");
                userInHomeVo.setWeight(weight);
                userInHomeVo.setBMI(BMI);
                userInHomeVo.setPsychology(psychologyResultService.getAdvice(userId));
                userInHomeVo.setRecipe(recipeResultService.getAdvice(userId));
                userInHomeVo.setSports(sportsResultService.getAdvice(userId));
                userInHomeVo.setTraditionalChineseMedicine(chineseMedicineService.getAdvice(userId));
            }
            return userInHomeVo;
        }
        return null;
    }

    private String getStandardBloodPressure(String[] highAndLowPressure) {
        String high = highAndLowPressure[0];
        String low = highAndLowPressure[1];
        try {
            int highInt = Integer.parseInt(high);
            int lowInt = Integer.parseInt(low);
            if (highInt < 120 && lowInt < 80) {
                return "正常血压";
            } else if ((highInt >= 120 && highInt <= 139) && (lowInt >= 80 && lowInt <= 89)) {
                return "正常高值";
            } else if ((highInt >= 140 && highInt <= 159) && (lowInt >= 90 && lowInt <= 99)) {
                return "轻度高血压";
            } else if ((highInt >= 160 && highInt <= 179) && (lowInt >= 100 && lowInt <= 109)) {
                return "中度高血压";
            } else if (highInt >= 140 && lowInt < 90) {
                return "单纯收缩期高血压";
            } else if (highInt >= 180 || lowInt >= 110) {
                return "重度高血压";
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    private void getDiff(UserInHomeVo userInHomeVo, String weight, double bmiDouble, String secondWeight, double secondBmiDouble) {
        userInHomeVo.setWeightDiff(String.format("%.1f", Double.parseDouble(weight) - Double.parseDouble(secondWeight)));
        userInHomeVo.setBMIDiff(String.format("%.1f", bmiDouble - secondBmiDouble));
    }

    public double getBmi(String height, String weight) {
        return Double.parseDouble(weight) / (Math.pow(Double.parseDouble(height) / 100, 2));
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

    public List<DataCountByMouthVo> findUserRegisterVo(String startDate, String endDate) {
        return userInformationMapper.findUserRegisterVo(startDate, endDate);
    }

    public Double getKcal(Double emt, Double minute, Double weight) {
        return emt * minute * weight;
    }
}
