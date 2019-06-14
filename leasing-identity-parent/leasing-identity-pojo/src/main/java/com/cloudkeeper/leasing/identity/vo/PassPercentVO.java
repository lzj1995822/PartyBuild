package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassPercentVO implements Serializable {
    /** 镇名 */
    @ApiModelProperty(value = "镇名", position = 10, required = true)
    private String tn;

    /** 镇编码 */
    @ApiModelProperty(value = "镇编码", position = 10, required = true)
    private String townCode;

    /** 镇名 */
    @ApiModelProperty(value = "待审核", position = 10, required = true)
    private Integer waitCheck;

    /** 通过 */
    @ApiModelProperty(value = "通过", position = 10, required = true)
    private Integer passed;

    /** 未完成 */
    @ApiModelProperty(value = "未完成", position = 10, required = true)
    private Integer fail;

    /** 比例 */
    @ApiModelProperty(value = "比例", position = 10, required = true)
    private BigDecimal finishRatio;
}
