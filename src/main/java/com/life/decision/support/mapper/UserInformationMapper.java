package com.life.decision.support.mapper;

import com.github.pagehelper.Page;
import com.life.decision.support.pojo.UserInformation;
import org.apache.ibatis.annotations.Mapper;


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

    UserInformation getUser(UserInformation userInformation);

    void delete(UserInformation userInformation);

    void updateById(UserInformation userInformation);

    void insert(UserInformation userInformation);

    Page<UserInformation> findList(UserInformation userInformation);
}