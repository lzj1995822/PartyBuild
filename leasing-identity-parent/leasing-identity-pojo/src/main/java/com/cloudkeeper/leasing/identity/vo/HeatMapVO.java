package com.cloudkeeper.leasing.identity.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeatMapVO {

    //组织id
    private String districtId;

    //组织名称
    private String districtName;

    //坐标位置
    private String location;

    //总数
    private Integer total;
}
