package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 村考核指标 VO
 * @author yujian
 */
@ApiModel(value = "村考核指标 VO", description = "村考核指标 VO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KPIVillageQuotaVO extends BaseVO {

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

    private String score;

    private String scoreEnd;

    private String formulaScore;


}
