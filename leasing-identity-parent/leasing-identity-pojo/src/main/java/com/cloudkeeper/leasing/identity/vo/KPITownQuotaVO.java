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
 * 镇考核指标 VO
 * @author yujian
 */
@ApiModel(value = "镇考核指标 VO", description = "镇考核指标 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPITownQuotaVO extends BaseVO {

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String districtId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

    /** 任务ID */
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    private String taskId;

    /** 指标名称 */
    @ApiModelProperty(value = "指标名称", position = 10, required = true)
    private String quotaName;

    /** 父指标标识 */
    @ApiModelProperty(value = "父指标标识", position = 10, required = true)
    private String parentQuotaId;

    /** 父指标名称 */
    @ApiModelProperty(value = "父指标名称", position = 10, required = true)
    private String parentQuotaName;

    /** 分值 */
    @ApiModelProperty(value = "分值", position = 10, required = true)
    private String score;

    List<KPIVillageQuotaVO> kpiVillageQuotas;

}
