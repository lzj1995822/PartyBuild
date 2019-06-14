package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
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
 * 考核积分 VO
 * @author lxw
 */
@ApiModel(value = "考核积分 VO", description = "考核积分 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExaScoreVO extends BaseVO {

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String organizationId;

    /** 活动ID */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    private String activityId;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Integer score;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 活动时间 */
    @ApiModelProperty(value = "活动时间", position = 10, required = true)
    private LocalDate activityTime;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    private LocalDateTime createTime;

}