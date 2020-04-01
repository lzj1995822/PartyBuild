package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "统计参数", description = "统计参数")
@Getter
@Setter
public class StatisticsStringVO {

    private String name;
    private String val;
}
