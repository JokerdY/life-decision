package com.life.decision.support.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.mapper.UserInformationMapper;
import com.life.decision.support.pojo.PassWordChangeDto;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.IUserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserInformationMapper userInformationMapper;

    @Override
    public List<UserInformationDto> findList(UserInformationDto userInformationDto) {
        UserInformation temp = BeanUtil.copyProperties(userInformationDto, UserInformation.class);
        PageHelper.startPage(userInformationDto);
        return userInformationMapper.findList(temp);
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
    public UserInformationDto getUserMsg(UserInformation userInformation) {
        UserInformationDto user = userInformationMapper.getUser(userInformation);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
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
