package com.life.decision.support.mapper;

import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.pojo.PassWordChangeDto;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.vo.DataCountByMouthVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    int delete(UserInformation userInformation);

    int update(UserInformation userInformation);

    int insert(UserInformation userInformation);

    List<UserInformationDto> findList(UserInformation userInformation);

    int verifyIdentified(UserInformation userInformation);

    int changePassword(PassWordChangeDto dto);

    List<DataCountByMouthVo> findUserRegisterVo(@Param("startDate") String startDate,
                                                @Param("endDate") String endDate);
}