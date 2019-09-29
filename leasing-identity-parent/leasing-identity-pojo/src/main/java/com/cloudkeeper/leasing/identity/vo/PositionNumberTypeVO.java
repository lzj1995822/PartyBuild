package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "阵地个数type VO", description = "阵地个数阵地个数type VO")
@Getter
@Setter
public class PositionNumberTypeVO {
    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 数量 */
    @ApiModelProperty(value = "数量", position = 10, required = true)
    private Integer number = 0;

}
