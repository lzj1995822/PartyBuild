package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@ApiModel(value = "云图地图活动完成率VO", description = "云图地图活动完成率VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CloudActivityRateVO {

    /** 镇ID */
    @ApiModelProperty(value = "镇ID", position = 10, required = true)
    private String attachTo;

    /** 完成数量 */
    @ApiModelProperty(value = "完成数量", position = 10, required = true)
    private Integer finished;

    /** 总数量 */
    @ApiModelProperty(value = "总数量", position = 10, required = true)
    private Integer total;

    /** 完成率 */
    @ApiModelProperty(value = "完成率", position = 10, required = true)
    private Double rate;

    /** 镇名 */
    @ApiModelProperty(value = "镇名", position = 10, required = true)
    private String districtName;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 10, required = true)
    private String location;

    public String getAttachTo() {
        return attachTo;
    }

    public void setAttachTo(String attachTo) {
        this.attachTo = attachTo;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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
}
