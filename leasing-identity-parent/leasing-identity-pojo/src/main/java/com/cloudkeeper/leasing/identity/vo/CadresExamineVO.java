package com.cloudkeeper.leasing.identity.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CadresExamineVO {

    private Integer number;
    private String districtName;
    private List<Map<String,String>> ids;
}
