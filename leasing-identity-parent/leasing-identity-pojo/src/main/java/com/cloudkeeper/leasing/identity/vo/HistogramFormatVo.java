package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@ApiModel(value = "云图柱状图 VO", description = "云图柱状图 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class HistogramFormatVo {

    /** 图例 */
    @ApiModelProperty(value = "图例", position = 10, required = true)
    private String name;

    /** 创建时间 */
    @ApiModelProperty(value = "对其方式", position = 10, required = true)
    private List<Integer> data;

}
