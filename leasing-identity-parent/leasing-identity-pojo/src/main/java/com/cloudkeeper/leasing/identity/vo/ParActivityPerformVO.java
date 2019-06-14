package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
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
 * 任务执行记录 VO
 * @author lxw
 */
@ApiModel(value = "任务执行记录 VO", description = "任务执行记录 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityPerformVO extends BaseVO {

    /** 活动ID */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    private String activityID;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String organizationId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 数据源 */
    @ApiModelProperty(value = "数据源", position = 10, required = true)
    private Integer source;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

}
