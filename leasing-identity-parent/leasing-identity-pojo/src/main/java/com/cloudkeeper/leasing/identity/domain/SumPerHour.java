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
import java.time.LocalDateTime;

/**
 * 每小时人流量
 * @author asher
 */
@ApiModel(value = "每小时人流量", description = "每小时人流量")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.Sum_Per_Hour")
public class SumPerHour extends BaseEntity {

    /** 每小时统计人流总数 */
    @ApiModelProperty(value = "每小时统计人流总数", position = 10, required = true)
    private Integer total;

    @ApiModelProperty(value = "开始时间", position = 10, required = true)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间", position = 10, required = true)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "硬件编码")
    private String locationCode;

    @ApiModelProperty(value = "阵地id")
    private String positionId;

}