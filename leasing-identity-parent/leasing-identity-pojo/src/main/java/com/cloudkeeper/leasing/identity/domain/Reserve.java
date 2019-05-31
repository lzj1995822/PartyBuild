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

/**
 * 后备人才
 * @author asher
 */
@ApiModel(value = "后备人才", description = "后备人才")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Team_Reserve")
public class Reserve extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 13, required = true)
    private String sex;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 15, required = true)
    private String nation;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 17)
    private LocalDate birthday;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 19)
    private String nativePlace;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 21)
    private String education;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 23)
    private String graduated;

    /** 参加工作时间 */
    @ApiModelProperty(value = "参加工作时间", position = 25)
    private LocalDate workDate;

    /** 身份类型 */
    @ApiModelProperty(value = "身份类型", position = 27)
    private String category;

    /** 工作单位 */
    @ApiModelProperty(value = "工作单位", position = 29)
    private String unit;

    /** 基本家庭情况 */
    @ApiModelProperty(value = "基本家庭情况", position = 31)
    private String basicSituation;

    /** 个人简历 */
    @ApiModelProperty(value = "个人简历", position = 33)
    private String resume;

}