package com.cloudkeeper.leasing.identity.dto.kpitownquota;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
import com.cloudkeeper.leasing.identity.dto.kpivillagequota.KPIVillageQuotaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 镇考核指标 DTO
 * @author yujian
 */
@ApiModel(value = "镇考核指标 DTO", description = "镇考核指标 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPITownQuotaDTO extends BaseEditDTO {

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

    private String quarter;

    private String quotaYear;

    List<KPIVillageQuotaDTO> kpiVillageQuotas;

//    是指标制定0还是考核打分1
    private String isReviewOrMake;

}
