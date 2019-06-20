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
public class ExamScoreVO {
    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Integer exam;

    /** attachTo */
    @ApiModelProperty(value = "attachTo", position = 10, required = true)
    private String attachTo;

    /** 分数 */
    @ApiModelProperty(value = "村", position = 10, required = true)
    private String cun;

    /** 分数 */
    @ApiModelProperty(value = "镇", position = 10, required = true)
    private String town;

    /** 总数 */
    @ApiModelProperty(value = "总数", position = 10, required = true)
    private Integer total;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String districtId;

}
