package com.cloudkeeper.leasing.identity.dto.kpiattachment;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
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
 * 考核实施佐证材料 查询DTO
 * @author asher
 */
@ApiModel(value = "考核实施佐证材料 查询DTO", description = "考核实施佐证材料 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPIAttachmentSearchable extends BaseSearchable {

    /** 考核实施任务id */
    @ApiModelProperty(value = "考核实施任务id", position = 10, required = true)
    private String taskId;

    /** 指标id */
    @ApiModelProperty(value = "指标id", position = 10, required = true)
    private String quotaId;

    /** 佐证材料层级 */
    @ApiModelProperty(value = "佐证材料层级", position = 10, required = true)
    private String quotaLevel;

    /** 执行组织 */
    @ApiModelProperty(value = "执行组织", position = 10, required = true)
    private String districtId;

    /** 季度 */
    @ApiModelProperty(value = "季度", position = 10, required = true)
    private String quarter;

    /** 附件地址 */
    @ApiModelProperty(value = "附件地址", position = 10, required = true)
    private String address;

}