package com.life.decision.support.service;

import com.life.decision.support.dto.UserInformationDto;
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

    boolean isExist(UserInformation userInformation);

    boolean insertUser(UserInformation userInformation);

    boolean verifyIdentified(UserInformation userInformation);

    boolean resetPassword(UserInformation userInformation, int tailNumberDigits);

    UserInformationDto getUserMsg(UserInformation userInformation);

    boolean changePassword(UserInformation userInformation);

    boolean editPersonalInformation(UserInformation userInformation);
}
