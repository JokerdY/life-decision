package com.life.decision.support.service;

import com.life.decision.support.dto.UserInHomeVo;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.pojo.PassWordChangeDto;
import com.life.decision.support.pojo.UserInformation;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
public interface IUserInformationService {
    List<UserInformationDto> findList(UserInformationDto userInformation);

    List<UserInformationDto> findAllList(UserInformationDto userInformationDto);

    boolean isExist(UserInformation userInformation);

    boolean insertUser(UserInformation userInformation);

    boolean verifyIdentified(UserInformation userInformation);

    boolean resetPassword(UserInformation userInformation, int tailNumberDigits);

    UserInHomeVo getUserMsg(UserInformation userInformation);

    UserInformationDto getAdminUser(UserInformation userInformation);

    boolean changePassword(PassWordChangeDto userInformation);

    boolean editPersonalInformation(UserInformation userInformation);


}
