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

/**
 * 双向印证
 * @author yujian
 */
@ApiModel(value = "双向印证", description = "双向印证")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.KPI_Statistics")
public class KPIStatistics extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String districtId;
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    @Column(length = 60)
    private String districtName;
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    @Column(length = 60)
    private String taskId;
    @ApiModelProperty(value = "村级实绩排名", position = 10, required = true)
    @Column(length = 60)
    private String villagePerformance;
    @ApiModelProperty(value = "监测指标排名", position = 10, required = true)
    @Column(length = 60)
    private String monitoringIndex;
    @ApiModelProperty(value = "日常工作村级实绩监测指标", position = 10, required = true)
    @Column(length = 60)
    private String dvm;
    @ApiModelProperty(value = "能力研判", position = 10, required = true)
    @Column(length = 60)
    private String abilityJudgement;

    @ApiModelProperty(value = "日常工作", position = 10, required = true)
    @Column(length = 60)
    private String routine;

    @ApiModelProperty(value = "综合评议", position = 10, required = true)
    @Column(length = 60)
    private String comprehensiveEvaluation;

    @ApiModelProperty(value = "综合评议镇", position = 10, required = true)
    @Column(length = 60)
    private String comprehensiveEvaluationABC;

    @ApiModelProperty(value = "满意度", position = 10, required = true)
    @Column(length = 60)
    private String satisfactionDegree;
    @ApiModelProperty(value = "总排名", position = 10, required = true)
    @Column(length = 60)
    private String totalRanking;
}
