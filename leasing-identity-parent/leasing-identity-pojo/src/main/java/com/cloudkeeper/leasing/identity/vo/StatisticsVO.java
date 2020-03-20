package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "统计参数", description = "统计参数")
@Getter
@Setter
public class StatisticsVO {

    private String name;
    private Integer val;
    private String value;
}
