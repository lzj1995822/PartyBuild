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
 * 村一级指标统计 VO
 * @author yujian
 */
@ApiModel(value = "村一级指标统计 VO", description = "村一级指标统计 VO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KPIVillageStatisticsVO extends BaseVO {
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String districtId;

    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

    @ApiModelProperty(value = "父组织ID", position = 10, required = true)
    private String parentDistrictId;

    @ApiModelProperty(value = "父组织名称", position = 10, required = true)
    private String parentDistrictName;

    @ApiModelProperty(value = "指标ID", position = 10, required = true)
    private String quotaId;

    @ApiModelProperty(value = "指标名称", position = 10, required = true)
    private String quotaName;

    @ApiModelProperty(value = "父类指标ID", position = 10, required = true)
    private String parentQuotaId;

    @ApiModelProperty(value = "父类指标名称", position = 10, required = true)
    private String parentQuotaName;

    @ApiModelProperty(value = "分数", position = 10, required = true)
    private String score;

    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    private String taskId;

    @ApiModelProperty(value = "排名", position = 10, required = true)
    private Integer ranking;

    @ApiModelProperty(value = "村书记姓名", position = 10, required = true)
    private String cadresName;

    @ApiModelProperty(value = "指标层级", position = 10, required = true)
    private String quotaLevel;

    @ApiModelProperty(value = "镇内排名", position = 10, required = true)
    private String townRanking;

    @ApiModelProperty(value = "分区等级ABCD", position = 10, required = true)
    private String partitionLevel;

    @ApiModelProperty(value = "村级实绩老的得分", position = 10, required = true)
    private String oldScore;

    @ApiModelProperty(value = "年龄", position = 10, required = true)
    private Integer age;

    @ApiModelProperty(value = "村书记id")
    private String cadresId;

    @ApiModelProperty(value = "考核年份")
    private String quotaYear;
}