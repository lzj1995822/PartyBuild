package com.cloudkeeper.leasing.identity.dto.feedbackitemvalue;

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
 * 反馈配置项值 查询DTO
 * @author asher
 */
@ApiModel(value = "反馈配置项值 查询DTO", description = "反馈配置项值 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackItemValueSearchable extends BaseSearchable {

    /** 活动指派表id */
    @ApiModelProperty(value = "活动指派表id", position = 10, required = true)
    private String objectId;

    /** 关联的配置项id */
    @ApiModelProperty(value = "关联的配置项id", position = 10, required = true)
    private String itemId;

    /** 值的类型 */
    @ApiModelProperty(value = "值的类型", position = 10, required = true)
    private String type;

    /** 值 */
    @ApiModelProperty(value = "值", position = 10, required = true)
    private String value;

    /** 配置项名称 */
    @ApiModelProperty(value = "配置项名称", position = 10, required = true)
    private String name;

    /** code */
    @ApiModelProperty(value = "code", position = 10, required = true)
    private String code;

}