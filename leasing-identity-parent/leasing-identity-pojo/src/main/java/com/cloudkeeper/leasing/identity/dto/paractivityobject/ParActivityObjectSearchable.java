package com.cloudkeeper.leasing.identity.dto.paractivityobject;

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
 * 任务对象 查询DTO
 * @author lxw
 */
@ApiModel(value = "任务对象 查询DTO", description = "任务对象 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityObjectSearchable extends BaseSearchable {

    /** 活动Id */
    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    private String activityId;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String organizationId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 是否在用 */
    @ApiModelProperty(value = "是否在用", position = 10, required = true)
    private String isWorking;

    /** 关联组织 */
    @ApiModelProperty(value = "关联组织", position = 10, required = true)
    private String attachTo;

    /** 活动当前状态 */
    @ApiModelProperty(value = "活动当前状态", position = 10, required = true)
    private String currentStatus;

    /** 任务对象类型 */
    @ApiModelProperty(value = "任务对象类型", position = 10, required = true)
    private String objectType;
}
