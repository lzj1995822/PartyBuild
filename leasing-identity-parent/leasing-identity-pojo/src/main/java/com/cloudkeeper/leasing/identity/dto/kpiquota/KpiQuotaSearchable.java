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
}
