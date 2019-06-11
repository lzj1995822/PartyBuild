package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 任务对象 VO
 * @author lxw
 */
@ApiModel(value = "任务对象 VO", description = "任务对象 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityObjectVO extends BaseVO {

    /** 活动Id */
    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    private String activityId;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String organizationId;

    /** 组织主ID */
    @ApiModelProperty(value = "组织主ID", position = 10, required = true)
    private String districtId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 是否在用 */
    @ApiModelProperty(value = "是否在用", position = 10, required = true)
    private String isWorking;

    /** 关联组织 */
    @ApiModelProperty(value = "关联组织", position = 10, required = true)
    private String attachTo;

    /** 活动名称 */
    @ApiModelProperty(value = "活动名称", position = 10, required = true)
    private String title;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 党建或远教 */
    @ApiModelProperty(value = "党建或远教", position = 10, required = true)
    private String taskType;

    /** 截止日期 */
    @ApiModelProperty(value = "截止日期", position = 10, required = true)
    private LocalDate month;

    /** 要求 */
    @ApiModelProperty(value = "要求", position = 10, required = true)
    private String context;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Integer score;

    /** 组织名字 */
    @ApiModelProperty(value = "组织名字", position = 10, required = true)
    private String districtName;





}
