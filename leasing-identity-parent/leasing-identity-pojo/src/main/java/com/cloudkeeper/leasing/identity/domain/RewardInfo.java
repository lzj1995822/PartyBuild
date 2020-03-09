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
 * 报酬情况
 * @author asher
 */
@ApiModel(value = "报酬情况", description = "报酬情况")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.Reward_Info")
public class RewardInfo extends BaseEntity {

    /** 干部id */
    @ApiModelProperty(value = "干部id", position = 5)
    private String cadresId;

    /** 获取时间 */
    @ApiModelProperty(value = "获取时间", position = 1)
    private LocalDate achieveTime;

    /** 基本报酬 */
    @ApiModelProperty(value = "基本报酬", position = 5)
    private String basicReward;

    /** 考核报酬 */
    @ApiModelProperty(value = "考核报酬", position = 10)
    private String reviewReward;

    /** 增收奖励 */
    @ApiModelProperty(value = "增收奖励", position = 15)
    private String otherReward;

    /** 合计 */
    @ApiModelProperty(value = "合计", position = 15)
    private String totalReward;

    /** 佐证材料 */
    @ApiModelProperty(value = "佐证材料", position = 20)
    private String supportDoc;

    /** 是否可以编辑 */
    @ApiModelProperty(value = "是否可以编辑", position = 25)
    private String isEdit;

    /** 描述（字段备用） */
    @ApiModelProperty(value = "描述（字段备用）", position = 30)
    private String des;

}