package com.cloudkeeper.leasing.identity.dto.parmember;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
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
 * 党员管理 查询DTO
 * @author cqh
 */
@ApiModel(value = "党员管理 查询DTO", description = "党员管理 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParMemberSearchable extends BaseSearchable {

    /** 地址 */
    @ApiModelProperty(value = "地址", position = 10, required = true)
    private String address;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10, required = true)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 10, required = true)
    private String sex;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 10, required = true)
    private LocalDateTime birth;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 10, required = true)
    private String nativePlace;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 10, required = true)
    private String nation;

    /** 参加工作时间 */
    @ApiModelProperty(value = "参加工作时间", position = 10, required = true)
    private LocalDateTime workTime;

    /** 身份证 */
    @ApiModelProperty(value = "身份证", position = 10, required = true)
    private String identityCard;

    /** 所在单位 */
    @ApiModelProperty(value = "所在单位", position = 10, required = true)
    private String company;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 10, required = true)
    private String education;

    /** 入学时间 */
    @ApiModelProperty(value = "入学时间", position = 10, required = true)
    private LocalDateTime admissionTime;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 10, required = true)
    private LocalDateTime graduationTime;

    /** 学位 */
    @ApiModelProperty(value = "学位", position = 10, required = true)
    private String academicDegree;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10, required = true)
    private String contact;

    /** 是否为困难党员 */
    @ApiModelProperty(value = "是否为困难党员", position = 10, required = true)
    private String isDifficulty;

    /** 是否为党员志愿者 */
    @ApiModelProperty(value = "是否为党员志愿者", position = 10, required = true)
    private String isVolunteer;

    /** 所属组织 */
    @ApiModelProperty(value = "所属组织", position = 33)
    private String districtId;

}