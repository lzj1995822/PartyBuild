package com.cloudkeeper.leasing.identity.dto.paractivity;

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
 * 活动 查询DTO
 * @author lxw
 */
@ApiModel(value = "活动 查询DTO", description = "活动 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivitySearchable extends BaseSearchable {

    /** 截止日期 */
    @ApiModelProperty(value = "截止日期", position = 10, required = true)
    private LocalDate month;

    /** 背景 */
    @ApiModelProperty(value = "背景", position = 10, required = true)
    private String context;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String title;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 区ID */
    @ApiModelProperty(value = "区ID", position = 10, required = true)
    private String districtID;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型", position = 10, required = true)
    private String taskType;

    /** 释放时间 */
    @ApiModelProperty(value = "释放时间", position = 10, required = true)
    private LocalDateTime releaseTime;

    /** 报警时间 */
    @ApiModelProperty(value = "报警时间", position = 10, required = true)
    private LocalDateTime alarmTime;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Integer score;

    /** 当前状态 */
    @ApiModelProperty(value = "当前状态", position = 10, required = true)
    private String currentStatus;

    /** 等级 */
    @ApiModelProperty(value = "等级", position = 10, required = true)
    private Integer districtLevel;

    @ApiModelProperty(value="任务对象类型",position = 10,required = true)
    private String objectType;


}
