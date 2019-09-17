package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.ParDifficultyVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 * 困难党员
 * @author cqh
 */
@ApiModel(value = "困难党员", description = "困难党员")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAR_Difficulty")
public class ParDifficulty extends BaseEntity {

    /** 党员ID */
    @ApiModelProperty(value = "党员", position = 10, required = true)
    @Column(length = 60)
    private String partyMemberId;

    /** 当前是否困难 */
    @ApiModelProperty(value = "当前是否困难", position = 10, required = true)
    @Column(length = 60)
    private String isDifficulty;

    /** 组织认定 */
    @ApiModelProperty(value = "组织认定", position = 10, required = true)
    @Column(length = 60)
    private String organizationalIdentity;

    /** 收入来源 */
    @ApiModelProperty(value = "收入来源", position = 10, required = true)
    @Column(length = 60)
    private String incomeSource;

    /** 年均收入 */
    @ApiModelProperty(value = "年均收入", position = 10, required = true)
    @Column(length = 60)
    private String averageIncome;

    /** 贫困原因 */
    @ApiModelProperty(value = "贫困原因", position = 10, required = true)
    @Column(length = 60)
    private String povertyCauses;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 组织名称*/
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    @Column(length = 60)
    private String districtName;

    /** 组织名称*/
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    @Column(length = 60)
    private String memberName;





}