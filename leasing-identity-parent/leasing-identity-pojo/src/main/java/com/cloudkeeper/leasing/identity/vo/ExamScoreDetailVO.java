package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamScoreDetailVO {

    @ApiModelProperty(value = "组织Id", position = 10, required = true)
    private String organizationId;

    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    private String activityId;

    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String title;

    @ApiModelProperty(value = "内容", position = 10, required = true)
    private String context;

    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String sta;

    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Integer score;

    @ApiModelProperty(value = "村名", position = 10, required = true)
    private String districtName;

}
