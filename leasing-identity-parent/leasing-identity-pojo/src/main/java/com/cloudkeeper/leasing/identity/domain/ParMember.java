package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 党员管理
 * @author cqh
 */
@ApiModel(value = "党员管理", description = "党员管理")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAR_Member")
public class ParMember extends BaseEntity {

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 10, required = true)
    @Column(length = 60)
    private String sex;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime birth;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 10, required = true)
    @Column(length = 60)
    private String nativePlace;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 10, required = true)
    @Column(length = 60)
    private String nation;

    /** 参加工作时间 */
    @ApiModelProperty(value = "参加工作时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime workTime;

    /** 身份证 */
    @ApiModelProperty(value = "身份证", position = 10, required = true)
    @Column(length = 60)
    private String identityCard;

    /** 所在单位 */
    @ApiModelProperty(value = "所在单位", position = 10, required = true)
    @Column(length = 60)
    private String company;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 10, required = true)
    @Column(length = 60)
    private String education;

    /** 入学时间 */
    @ApiModelProperty(value = "入学时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime admissionTime;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime graduationTime;

    /** 学位 */
    @ApiModelProperty(value = "学位", position = 10, required = true)
    @Column(length = 60)
    private String academicDegree;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10, required = true)
    @Column(length = 60)
    private String contact;

    /** 是否为困难党员 */
    @ApiModelProperty(value = "是否为困难党员", position = 10, required = true)
    @Column(length = 60)
    private String isDifficulty;

    /** 是否为党员志愿者 */
    @ApiModelProperty(value = "是否为党员志愿者", position = 10, required = true)
    @Column(length = 60)
    private String isVolunteer;

}