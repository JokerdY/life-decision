package com.life.decision.support.mapper;

import com.github.pagehelper.Page;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.pojo.UserInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Joker
 * @since 2022-03-06
 */
@Mapper
public interface UserInformationMapper {

    UserInformationDto getUser(UserInformation userInformation);

    void delete(UserInformation userInformation);

    void updateById(UserInformation userInformation);

    void insert(UserInformation userInformation);

    List<UserInformationDto> findList(UserInformation userInformation);
}