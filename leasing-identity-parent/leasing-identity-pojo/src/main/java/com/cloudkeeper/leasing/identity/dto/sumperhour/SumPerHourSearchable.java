package com.cloudkeeper.leasing.identity.dto.sumperhour;

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
 * 每小时人流量 查询DTO
 * @author asher
 */
@ApiModel(value = "每小时人流量 查询DTO", description = "每小时人流量 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SumPerHourSearchable extends BaseSearchable {

    /** 每小时统计人流总数 */
    @ApiModelProperty(value = "每小时统计人流总数", position = 10, required = true)
    private Integer total;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 10, required = true)
    private LocalDateTime startTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 10, required = true)
    private LocalDateTime endTime;

    /** 硬件编码 */
    @ApiModelProperty(value = "硬件编码", position = 0)
    private String locationCode;

    /** 阵地id */
    @ApiModelProperty(value = "阵地id", position = 0)
    private String positionId;

}