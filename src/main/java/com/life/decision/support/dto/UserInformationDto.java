package com.life.decision.support.dto;

import com.life.decision.support.common.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class UserInformationDto extends PageDto {

    @ApiModelProperty(value = "id")
    private String id;

    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String telphoneNum;

    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 1:admin 0:user
     */
    @ApiModelProperty(value = "管理员字典 0:普通用户 1:管理员")
    private Integer adminEnable;

    /**
     * 0:female 1:male
     */
    @ApiModelProperty(value = "性别字典")
    private Integer sex;

    @ApiModelProperty(value = "出生日期")
    private Date dateOfBirth;

    @ApiModelProperty(value = "升高")
    private String height;

    @ApiModelProperty(value = "体重")
    private String weight;

    /**
     * 教育程度
     */
    @ApiModelProperty(value = "受教育程度字典")
    private Integer educationalLevel;

    /**
     * 职业
     */
    @ApiModelProperty(value = "职业字典")
    private Integer occupation;

    @ApiModelProperty("结婚状态字典")
    private Integer marital;

    @ApiModelProperty("家庭收入字典")
    private Integer householdIncome;
    /**
     * 身份
     */
    @ApiModelProperty(value = "管理员展示值")
    private String identity;

    @ApiModelProperty(value = "bmi")
    private String BMI;

    @ApiModelProperty("教育程度展示值")
    private String educationalLevelDto;

    @ApiModelProperty("职业展示值")
    private String occupationDto;

    @ApiModelProperty("性别展示值")
    private String sexDto;

    @ApiModelProperty("结婚状态展示值")
    private String maritalDto;

    @ApiModelProperty("家庭收入展示值")
    private String householdIncomeDto;

    private String age;

    public String getAge() {
        LocalDate localDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        return localDate.until(now).getYears() + "";
    }
}
