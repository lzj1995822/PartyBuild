package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "统计每天单位阵地内的人流量VO", description = "统计每天单位阵地内的人流量VO")
@Getter
@Setter
public class StreamDayVO {

    @ApiModelProperty(value = "党员教育室", position = 10, required = true)
    private Integer memberEducation;

    @ApiModelProperty(value = "党群工作室", position = 10, required = true)
    private Integer partyStudio;

    @ApiModelProperty(value = "组织议事室", position = 10, required = true)
    private Integer organizationalConference;

    @ApiModelProperty(value = "党内关爱室", position = 10, required = true)
    private Integer partyCare;

    @ApiModelProperty(value = "每小时统计人流总数", position = 10, required = true)
    private String monthDay;

    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;


}
