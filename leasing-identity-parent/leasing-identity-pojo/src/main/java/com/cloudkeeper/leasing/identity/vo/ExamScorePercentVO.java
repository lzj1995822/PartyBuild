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
public class ExamScorePercentVO {
    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Double score;

    /** attachTo */
    @ApiModelProperty(value = "attachTo", position = 10, required = true)
    private String attachTo;

    /** 分数 */
    @ApiModelProperty(value = "村", position = 10, required = true)
    private String cun;

    /** 分数 */
    @ApiModelProperty(value = "镇", position = 10, required = true)
    private String town;

}

