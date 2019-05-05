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

/**
 * 活动
 * @author lxw
 */
@ApiModel(value = "活动", description = "活动")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_Activity")
public class ParActivity extends BaseEntity {

    /** 截止日期 */
    @ApiModelProperty(value = "截止日期", position = 10, required = true)
    @Column(length = 60)
    private String month;

    /** 背景 */
    @ApiModelProperty(value = "背景", position = 10, required = true)
    @Column(length = 60)
    private String context;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String title;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    @Column(length = 60)
    private String status;

    /** 区ID */
    @ApiModelProperty(value = "区ID", position = 10, required = true)
    @Column(length = 60)
    private String districtID;

    /** 截止日期 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    @Column(length = 60)
    private String type;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型", position = 10, required = true)
    @Column(length = 60)
    private String taskType;

    /** 释放时间 */
    @ApiModelProperty(value = "释放时间", position = 10, required = true)
    @Column(length = 60)
    private String releaseTime;

    /** 报警时间 */
    @ApiModelProperty(value = "报警时间", position = 10, required = true)
    @Column(length = 60)
    private String alarmTime;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    @Column(length = 60)
    private Integer score;


}
