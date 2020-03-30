package com.cloudkeeper.leasing.identity.dto.kpivillagestatistics;

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
 * 村一级指标统计 DTO
 * @author yujian
 */
@ApiModel(value = "村一级指标统计 DTO", description = "村一级指标统计 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPIVillageStatisticsDTO extends BaseEditDTO {

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String districtId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

    /** 父组织ID */
    @ApiModelProperty(value = "父组织ID", position = 10, required = true)
    private String parentDistrictId;

    /** 父组织名称 */
    @ApiModelProperty(value = "父组织名称", position = 10, required = true)
    private String parentDistrictName;

    /** 指标ID */
    @ApiModelProperty(value = "指标ID", position = 10, required = true)
    private String quotaId;

    /** 指标名称 */
    @ApiModelProperty(value = "指标名称", position = 10, required = true)
    private String quotaName;

    /** 父类指标ID */
    @ApiModelProperty(value = "父类指标ID", position = 10, required = true)
    private String parentQuotaId;

    /** 父类指标名称 */
    @ApiModelProperty(value = "父类指标名称", position = 10, required = true)
    private String parentQuotaName;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private String score;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 10, required = true)
    private String weight;

    /** 任务ID */
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    private String taskId;

    /** 季节 */
    @ApiModelProperty(value = "季节", position = 10, required = true)
    private String quarter;

}