package com.cloudkeeper.leasing.identity.dto.kpiquota;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 村主任考核指标 查询DTO
 * @author yujian
 */
@ApiModel(value = "村主任考核指标 查询DTO", description = "村主任考核指标 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KpiQuotaSearchable extends BaseSearchable {

    /** 指标名称 */
    @ApiModelProperty(value = "指标名称", position = 10, required = true)
    private String quotaName;

    /** 父指标ID */
    @ApiModelProperty(value = "父指标ID", position = 10, required = true)
    private String parentQuotaId;

    @ApiModelProperty(value = "指标标识", position = 10, required = true)
    private String quotaId;

    @ApiModelProperty(value = "指标年度", position = 10, required = true)
    private String quotaYear;

    @ApiModelProperty(value = "指标层级", position = 10, required = true)
    private String quotaLevel;

    @ApiModelProperty(value = "指标分值", position = 10, required = true)
    private String quotaScore;

    @ApiModelProperty(value = "指标是否需要设置权重", position = 10, required = true)
    private String isSetWeight;

    @ApiModelProperty(value = "指标制定部门", position = 10, required = true)
    private String quotaMakeDepartId;

    @ApiModelProperty(value = "指标打分部门", position = 10, required = true)
    private String quotaScoringDepartId;

    @ApiModelProperty(value = "指标积分方法", position = 10, required = true)
    private String quotaIntegrationMethod;

    @ApiModelProperty(value = "指标制定部门", position = 10, required = true)
    private String quotaMakeDepartName;

    @ApiModelProperty(value = "指标打分部门", position = 10, required = true)
    private String quotaScoringDepartName;

    @ApiModelProperty(value = "指标内容", position = 10, required = true)
    private String quotaContent;

    @ApiModelProperty(value = "多次执行还是一次执行", position = 10, required = true)
    private String onceOrMore;

    @ApiModelProperty(value = "季度", position = 10, required = true)
    private String quarter;

    @ApiModelProperty(value = "任务id", position = 10, required = true)
    private String taskId;

    @ApiModelProperty(value = "当前选择的单位查询佐证材料的", position = 10, required = true)
    private String districtId;
}
