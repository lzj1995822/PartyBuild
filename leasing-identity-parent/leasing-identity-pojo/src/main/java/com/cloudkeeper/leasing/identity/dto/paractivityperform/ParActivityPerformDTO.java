package com.cloudkeeper.leasing.identity.dto.paractivityperform;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
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
 * 任务执行记录 DTO
 * @author lxw
 */
@ApiModel(value = "任务执行记录 DTO", description = "任务执行记录 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityPerformDTO extends BaseEditDTO {

    /** 活动ID */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    private String activityID;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String organizationId;

    /** 组织短ID */
    @ApiModelProperty(value = "组织短ID", position = 10, required = true)
    private String districtId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 数据源 */
    @ApiModelProperty(value = "数据源", position = 10, required = true)
    private Integer source;

    /** 审核说明 */
    @ApiModelProperty(value = "审核说明", position = 10, required = true)
    private String remark;

}
