package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModelProperty;

public class PassPercentVO {
    /** 镇名 */
    @ApiModelProperty(value = "镇名", position = 10, required = true)
    private String tn;

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
    private Float finishRatio;
}
