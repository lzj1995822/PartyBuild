package com.cloudkeeper.leasing.identity.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CadresGroupByLevelVO  {

    private String levelName;

    private List<Map<String, String>> cadres;

}
