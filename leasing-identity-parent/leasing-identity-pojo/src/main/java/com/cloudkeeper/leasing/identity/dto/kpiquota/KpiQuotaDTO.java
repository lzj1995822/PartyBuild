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

    private List<KPITownQuotaDTO> kpiTownQuotas;

}
