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

    /** 父指标ID */
    @ApiModelProperty(value = "父指标ID", position = 10, required = true)
    private String parentQuotaId;
    @ApiModelProperty(value = "子类", position = 19)
    private List<KPITownQuotaVO> kpiTownQuotas;

    @ApiModelProperty(value = "指标标识", position = 10, required = true)
    private String quotaId;

    @ApiModelProperty(value = "指标年度", position = 10, required = true)
    private String quotaYear;

    @ApiModelProperty(value = "指标层级", position = 10, required = true)
    private String quotaLevel;

    private List<KpiQuotaVO> kpiQuotas;
}
