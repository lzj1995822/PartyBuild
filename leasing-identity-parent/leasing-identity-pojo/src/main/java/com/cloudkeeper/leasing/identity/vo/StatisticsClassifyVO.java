package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@ApiModel(value = "统计参数分类", description = "统计参数分类")
@Getter
@Setter
public class StatisticsClassifyVO {
    private String name;
    private List<StatisticsNotIntegerVO> statistics;
}
