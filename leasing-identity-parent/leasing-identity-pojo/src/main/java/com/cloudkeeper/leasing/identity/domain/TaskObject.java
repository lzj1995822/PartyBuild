package com.cloudkeeper.leasing.identity.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskObject implements Serializable {

    /** 市ID */
    @ApiModelProperty(value = "市ID", position = 10, required = true)
    private List<String> sid;

    /** 镇ID */
    @ApiModelProperty(value = "镇ID", position = 10, required = true)
    private List<String> zid;

    /** 村ID */
    @ApiModelProperty(value = "村ID", position = 10, required = true)
    private List<String> cid;

}
