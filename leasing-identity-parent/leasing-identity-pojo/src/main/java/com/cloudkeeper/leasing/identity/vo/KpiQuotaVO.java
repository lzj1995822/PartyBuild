package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 村主任考核指标 VO
 * @author yujian
 */
@ApiModel(value = "村主任考核指标 VO", description = "村主任考核指标 VO")
@Getter
@Setter
@Accessors(chain = true)
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
    private List<KPITownQuotaVO> kpiTownQuotaVOS;

    @ApiModelProperty(value = "指标标识", position = 10, required = true)
    private String quotaId;
}
