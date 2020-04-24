package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@ApiModel(value = "统计参数", description = "统计参数")
@Getter
@Setter
public class StatisticsListVO {
    private String name;

    private String v1;
    private String v2;
    private String V3;
    private List<StatisticsVO> statistics;
}
