package com.cloudkeeper.leasing.identity.dto.kpiquota;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 村主任考核指标 DTO
 * @author yujian
 */
@ApiModel(value = "村主任考核指标 DTO", description = "村主任考核指标 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KpiQuotaDTO extends BaseEditDTO {

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

    private List<KPITownQuotaDTO> kpiTownQuotas;

}
