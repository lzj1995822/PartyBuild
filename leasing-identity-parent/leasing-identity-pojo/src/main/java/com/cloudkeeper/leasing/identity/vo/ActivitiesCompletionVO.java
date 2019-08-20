package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesCompletionVO  extends BaseVO {


    @ApiModelProperty(value = "活动id", position = 10, required = true)
    private String activityId;

    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String  status;

    @ApiModelProperty(value = "组织id", position = 10, required = true)
    private String organizationId;

    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

    @ApiModelProperty(value = "组织类别", position = 10, required = true)
    private String districtType;

    @ApiModelProperty(value = "object表的id", position = 10, required = true)
    private String objectId;
}
