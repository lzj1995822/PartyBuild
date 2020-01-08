package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel(value = "当月活动VO", description = "当月活动 VO")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrentObjectVo {

    /** 活动 */
    @ApiModelProperty(value = "活动id", position = 10, required = true)
    private String activityId;

    /** 村名 */
    @ApiModelProperty(value = "村名", position = 10, required = true)
    private String districtName;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
