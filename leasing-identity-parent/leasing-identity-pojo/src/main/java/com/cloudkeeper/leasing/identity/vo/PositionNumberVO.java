package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(value = "阵地个数 VO", description = "阵地个数 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PositionNumberVO {
    /** 党员教育 */
    @ApiModelProperty(value = "党员教育", position = 10, required = true)
    private Integer memberEducation = 0;

    /** 党内关爱室 */
    @ApiModelProperty(value = "党内关爱室", position = 10, required = true)
    private Integer partyCare = 0;

    /** 组织议事室 */
    @ApiModelProperty(value = "组织议事室", position = 10, required = true)
    private Integer organizationalConference = 0;

    /** 党群工作室 */
    @ApiModelProperty(value = "党群工作室", position = 10, required = true)
    private Integer partyStudio = 0;

    /** 厅 */
    @ApiModelProperty(value = "厅", position = 10, required = true)
    private Integer hall = 0;

    /** 厅 */
    @ApiModelProperty(value = "栏", position = 10, required = true)
    private Integer column = 0;

    /** 厅 */
    @ApiModelProperty(value = "广场", position = 10, required = true)
    private Integer square = 0;
}
