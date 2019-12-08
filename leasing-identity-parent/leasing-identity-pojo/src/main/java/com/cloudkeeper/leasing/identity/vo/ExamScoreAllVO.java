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
public class ExamScoreAllVO {
    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    private String districtId;

    /** 组织名 */
    @ApiModelProperty(value = "组织名", position = 10, required = true)
    private String districtName;

    /** 完成比例 */
    @ApiModelProperty(value = "完成比例", position = 10, required = true)
    private Double finishRatio;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Integer score;

    /** 层级 */
    @ApiModelProperty(value = "层级", position = 10, required = true)
    private Integer districtLevel;
}


