package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 村一级指标统计
 * @author yujian
 */
@ApiModel(value = "村一级指标统计", description = "村一级指标统计")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.KPI_Village_Statistics")
public class KPIVillageStatistics extends BaseEntity {

    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String districtId;
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    @Column(length = 60)
    private String districtName;
    @ApiModelProperty(value = "父组织ID", position = 10, required = true)
    @Column(length = 60)
    private String parentDistrictId;
    @ApiModelProperty(value = "父组织名称", position = 10, required = true)
    @Column(length = 60)
    private String parentDistrictName;
    @ApiModelProperty(value = "指标ID", position = 10, required = true)
    @Column(length = 60)
    private String quotaId;
    @ApiModelProperty(value = "指标名称", position = 10, required = true)
    @Column(length = 60)
    private String quotaName;
    @ApiModelProperty(value = "父类指标ID", position = 10, required = true)
    @Column(length = 60)
    private String parentQuotaId;
    @ApiModelProperty(value = "父类指标名称", position = 10, required = true)
    @Column(length = 60)
    private String parentQuotaName;
    @ApiModelProperty(value = "分数", position = 10, required = true)
    @Column(length = 60)
    private String score;
    @ApiModelProperty(value = "权重", position = 10, required = true)
    @Column(length = 60)
    private String weight;
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    @Column(length = 60)
    private String taskId;
    @ApiModelProperty(value = "季节", position = 10, required = true)
    @Column(length = 60)
    private String quarter;
    @ApiModelProperty(value = "排名", position = 10, required = true)
    @Column(length = 60)
    private String ranking;
}
