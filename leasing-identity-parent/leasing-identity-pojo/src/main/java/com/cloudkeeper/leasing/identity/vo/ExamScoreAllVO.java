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
    @ApiModelProperty(value = "组织id")
    private String districtId;

    /** 组织名 */
    @ApiModelProperty(value = "组织名")
    private String districtName;

    /** 完成比例 */
    @ApiModelProperty(value = "完成比例")
    private BigDecimal finishRatio;

    /** 分数 */
    @ApiModelProperty(value = "分数")
    private BigDecimal score;

    /** 总分（党委） */
    @ApiModelProperty(value = "总分（党委）")
    private Integer total = 0;

}


