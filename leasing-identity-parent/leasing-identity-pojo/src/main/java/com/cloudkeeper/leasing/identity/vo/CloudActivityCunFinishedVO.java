package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@ApiModel(value = "云图地图村当月完成情况VO", description = "云图地图村完成情况 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CloudActivityCunFinishedVO {
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getUnfinished() {
        return unfinished;
    }

    public void setUnfinished(Integer unfinished) {
        this.unfinished = unfinished;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /** 村Id */
    @ApiModelProperty(value = "村Id", position = 10, required = true)
    private String organizationId;

    /** 完成个数 */
    @ApiModelProperty(value = "完成个数", position = 10, required = true)
    private Integer finished;

    /** 未完成个数 */
    @ApiModelProperty(value = "未完成个数", position = 10, required = true)
    private Integer unfinished;

    /** 村名 */
    @ApiModelProperty(value = "村名", position = 10, required = true)
    private String districtName;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 10, required = true)
    private String location;


}
