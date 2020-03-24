package com.cloudkeeper.leasing.identity.dto.kpivillagequota;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 村考核指标 DTO
 * @author yujian
 */
@ApiModel(value = "村考核指标 DTO", description = "村考核指标 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPIVillageQuotaDTO extends BaseEditDTO {

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
