package com.cloudkeeper.leasing.identity.dto.villagecadres;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 村干部管理 查询DTO
 * @author cqh
 */
@ApiModel(value = "村干部管理统计 查询DTO", description = "村干部管理统计 查询DTO")
@Getter
@Setter
public class VillageCadresStatisticsSearchable extends BaseSearchable {

    @ApiModelProperty(value = "字段类型", position = 10, required = true)
    private String filedType;
    @ApiModelProperty(value = "字段名", position = 10, required = true)
    private String filedName;
    @ApiModelProperty(value = "字段中文名", position = 10, required = true)
    private String filedDesc;
    @ApiModelProperty(value = "比较符", position = 10, required = true)
    private String comparison;
    @ApiModelProperty(value = "值1", position = 10, required = true)
    private String valueMin;
    @ApiModelProperty(value = "值2-用于between", position = 10)
    private String valueMax;
}
