package com.cloudkeeper.leasing.identity.dto.volunteergroup;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 志愿者服务队伍 DTO
 * @author asher
 */
@ApiModel(value = "志愿者服务队伍 DTO", description = "志愿者服务队伍 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerGroupDTO extends BaseEditDTO {

    /** 服务队编号 */
    @ApiModelProperty(value = "服务队编号", position = 10, required = true)
    private String code;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

    /** 所属党组织 */
    @ApiModelProperty(value = "所属党组织", position = 10, required = true)
    private String partyBranchId;

    /** 所属单位 */
    @ApiModelProperty(value = "所属单位", position = 10, required = true)
    private String unit;

    /** 服务宗旨 */
    @ApiModelProperty(value = "服务宗旨", position = 10, required = true)
    private String tenet;

    /** 单位地址 */
    @ApiModelProperty(value = "单位地址", position = 10, required = true)
    private String unitAddress;

    /** 办公电话 */
    @ApiModelProperty(value = "办公电话", position = 10, required = true)
    private String officeNumber;

    /** 队长姓名 */
    @ApiModelProperty(value = "队长姓名", position = 10, required = true)
    private String captialName;

    /** 队长职务 */
    @ApiModelProperty(value = "队长职务", position = 10, required = true)
    private String captialPosition;

    /** 队长联系方式 */
    @ApiModelProperty(value = "队长联系方式", position = 10, required = true)
    private String captialContact;

    /** 联络员姓名 */
    @ApiModelProperty(value = "联络员姓名", position = 10, required = true)
    private String contactOfficer;

    /** 联络员职务 */
    @ApiModelProperty(value = "联络员职务", position = 10, required = true)
    private String officerPosition;

    /** 手机号码 */
    @ApiModelProperty(value = "手机号码", position = 10, required = true)
    private String phoneNumber;

    /** qq号码 */
    @ApiModelProperty(value = "qq号码", position = 10, required = true)
    private String qqNumber;

    /** 电子邮箱 */
    @ApiModelProperty(value = "电子邮箱", position = 10, required = true)
    private String email;

    /** 基本情况 */
    @ApiModelProperty(value = "基本情况", position = 10, required = true)
    private String basicSiution;

}