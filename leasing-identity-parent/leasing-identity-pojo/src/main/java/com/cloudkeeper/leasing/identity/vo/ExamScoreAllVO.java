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
    /** 村分数 */
    @ApiModelProperty(value = "村分数", position = 10, required = true)
    private Integer exam;

    /** 村名 */
    @ApiModelProperty(value = "村名", position = 10, required = true)
    private String cun;

    /** 镇 */
    @ApiModelProperty(value = "镇", position = 10, required = true)
    private String town;

    /** 村比例 */
    @ApiModelProperty(value = "村比例", position = 10, required = true)
    private Double score;

    /** 镇分数 */
    @ApiModelProperty(value = "镇分数", position = 10, required = true)
    private Integer townExam;

    /** 镇比例 */
    @ApiModelProperty(value = "镇比例", position = 10, required = true)
    private Double townScore;
}


