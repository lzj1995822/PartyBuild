package com.cloudkeeper.leasing.identity.dto.kpivillagequota;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 村考核指标 查询DTO
 * @author yujian
 */
@ApiModel(value = "村考核指标 查询DTO", description = "村考核指标 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPIVillageQuotaSearchable extends BaseSearchable {

    /** 镇指标id */
    @ApiModelProperty(value = "镇指标id", position = 10, required = true)
    private String townQuotaId;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 10, required = true)
    private String weight;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String districtId;

    /** 组织名 */
    @ApiModelProperty(value = "组织名", position = 10, required = true)
    private String districtName;

}
