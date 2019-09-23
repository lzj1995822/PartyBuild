package com.cloudkeeper.leasing.identity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "云图活动完成率 VO", description = "云图活动完成率 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDashboardFormatVo {

    /** 刻度最大值 */
    @ApiModelProperty(value = "刻度最大值", position = 10, required = true)
    private Integer max;

    /** 刻度最小值 */
    @ApiModelProperty(value = "刻度最小值", position = 10, required = true)
    private Integer min;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 10, required = true)
    private String name;

    /** 单位 */
    @ApiModelProperty(value = "单位", position = 10, required = true)
    private String unit;

    /** 值 */
    @ApiModelProperty(value = "值", position = 10, required = true)
    private Double value;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 10, required = true)
    private Boolean hideName;


}
