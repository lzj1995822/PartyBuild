package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 双向印证 VO
 * @author yujian
 */
@ApiModel(value = "双向印证 VO", description = "双向印证 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPIStatisticsVO extends BaseVO {

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String districtId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

    /** 任务ID */
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    private String taskId;

    /** 村级实绩排名 */
    @ApiModelProperty(value = "村级实绩排名", position = 10, required = true)
    private String villagePerformance;

    /** 监测指标排名 */
    @ApiModelProperty(value = "监测指标排名", position = 10, required = true)
    private String monitoringIndex;

    /** 日常工作村级实绩监测指标 */
    @ApiModelProperty(value = "日常工作村级实绩监测指标", position = 10, required = true)
    private String dvm;

    /** 能力研判 */
    @ApiModelProperty(value = "能力研判", position = 10, required = true)
    private String abilityJudgement;

    /** 日常工作 */
    @ApiModelProperty(value = "日常工作", position = 10, required = true)
    private String routine;

    /** 综合评议 */
    @ApiModelProperty(value = "综合评议", position = 10, required = true)
    private String comprehensiveEvaluation;

    /** 综合评议镇 */
    @ApiModelProperty(value = "综合评议镇", position = 10, required = true)
    private String comprehensiveEvaluationABC;

    /** 满意度 */
    @ApiModelProperty(value = "满意度", position = 10, required = true)
    private String satisfactionDegree;

    private int margin1;
    private int margin2;
    private int margin3;
    private int margin4;


}
