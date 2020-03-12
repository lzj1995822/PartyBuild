package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel(value = "统计参数", description = "统计参数")
@Getter
@Setter
public class StatisticsNotIntegerVO {

    private String name;
    private BigDecimal val;
}
