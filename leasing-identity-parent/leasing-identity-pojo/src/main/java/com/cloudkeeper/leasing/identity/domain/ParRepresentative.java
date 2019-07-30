package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.ParRepresentativeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 党代表
 * @author cqh
 */
@ApiModel(value = "党代表", description = "党代表")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAR_Representative")
public class ParRepresentative extends BaseEntity {

    /** 党员Id */
    @ApiModelProperty(value = "党员Id", position = 10, required = true)
    @Column(length = 60)
    private String partyMemberId;

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
    private LocalDate birth;

    /** 入党时间 */
    @ApiModelProperty(value = "入党时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate joinDate;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 10, required = true)
    @Column(length = 60)
    private String education;

    /** 技能或特长 */
    @ApiModelProperty(value = "技能或特长", position = 10, required = true)
    @Column(length = 60)
    private String talent;

    /** 个人简历 */
    @ApiModelProperty(value = "个人简历", position = 10, required = true)
    @Column(length = 60)
    private String resume;

    /** 主要表现 */
    @ApiModelProperty(value = "主要表现", position = 10, required = true)
    @Column(length = 60)
    private String performance;

    /** 政审情况 */
    @ApiModelProperty(value = "政审情况", position = 10, required = true)
    @Column(length = 60)
    private String politicalTrail;

    /** 奖惩情况 */
    @ApiModelProperty(value = "奖惩情况", position = 10, required = true)
    @Column(length = 60)
    private String rewardPunish;

    /** 单位意见 */
    @ApiModelProperty(value = "单位意见", position = 10, required = true)
    @Column(length = 60)
    private String opinion;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10, required = true)
    @Column(length = 60)
    private String remark;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 22, required = true)
    @Column(length = 36)
    private String districtId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 22, required = true)
    @Column(length = 36)
    private String districtName;
}
