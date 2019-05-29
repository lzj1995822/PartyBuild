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

public class TownDetailVO  implements Serializable {
    /** 镇名 */
    @ApiModelProperty(value = "镇名", position = 10, required = true)
    private String tn;

    /** 镇ID */
    @ApiModelProperty(value = "镇ID", position = 10, required = true)
    private String tnId;

    /** 村 */
    @ApiModelProperty(value = "村", position = 10, required = true)
    private String cn;

    /** 村ID  */
    @ApiModelProperty(value = "村ID", position = 10, required = true)
    private String cd;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String sa;

    /** 活动Id */
    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    private String aid;
}



