package com.cloudkeeper.leasing.identity.dto.kpievaluation;

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
 * 综合评议 查询DTO
 * @author yujian
 */
@ApiModel(value = "综合评议 查询DTO", description = "综合评议 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPIEvaluationSearchable extends BaseSearchable {

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 任务ID */
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    private String taskId;

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 10, required = true)
    private String index;

    /** 项目类别 */
    @ApiModelProperty(value = "项目类别", position = 10, required = true)
    private String commentProject;

    /** 项目内容 */
    @ApiModelProperty(value = "项目内容", position = 10, required = true)
    private String commentContent;

    /** 满意 */
    @ApiModelProperty(value = "满意", position = 10, required = true)
    private Integer good;

    /** 比较满意 */
    @ApiModelProperty(value = "比较满意", position = 10, required = true)
    private Integer preferably;

    /** 基本满意 */
    @ApiModelProperty(value = "基本满意", position = 10, required = true)
    private Integer commonly;

    /** 不满意 */
    @ApiModelProperty(value = "不满意", position = 10, required = true)
    private Integer bad;

    /** 村名 */
    @ApiModelProperty(value = "村名", position = 10, required = true)
    private String districtId;

}
