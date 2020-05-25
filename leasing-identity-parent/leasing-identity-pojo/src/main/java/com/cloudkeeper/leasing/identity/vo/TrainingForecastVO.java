package com.cloudkeeper.leasing.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingForecastVO {

    /** 值*/
    private Double val;

    /** 组织id*/
    private String districtId;

    /** 组织名称*/
    private String districtName;

    /** 村书记名称*/
    private String cadresName;
}
