package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VillageQoutaVO {
    @ApiModelProperty(value = "考核项名称", position = 10, required = true)
    private String quotaName;
    @ApiModelProperty(value = "分值", position = 10, required = true)
    private String score;

    private String id;
}
