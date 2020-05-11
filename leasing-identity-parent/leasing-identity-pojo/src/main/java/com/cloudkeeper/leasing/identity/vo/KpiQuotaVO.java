package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 村主任考核指标 VO
 * @author yujian
 */
@ApiModel(value = "村主任考核指标 VO", description = "村主任考核指标 VO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KpiQuotaVO extends BaseVO {

    /** 指标名称 */
    @ApiModelProperty(value = "指标名称", position = 10, required = true)
    private String quotaName;

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

    private List<KpiQuotaVO> kpiQuotas;

    @ApiModelProperty(value = "多次执行还是一次执行", position = 10, required = true)
    private String onceOrMore;

    /** 父指标ID */
    @ApiModelProperty(value = "父指标ID", position = 10, required = true)
    private String parentQuotaId;

    @ApiModelProperty(value = "子类", position = 19)
    private List<KPITownQuotaVO> kpiTownQuotas;

}
