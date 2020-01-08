package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@ApiModel(value = "党员echarts VO", description = "党员echarts VO")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParMemberChartsVo {

    /** 名字 */
    @ApiModelProperty(value = "名字", position = 10, required = true)
    private String name;

    /** 数量 */
    @ApiModelProperty(value = "数量", position = 10, required = true)
    private Integer sum;

    /** 比例 */
    @ApiModelProperty(value = "比例", position = 10, required = true)
    private BigDecimal ratio;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }


}
