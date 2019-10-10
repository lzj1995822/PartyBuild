package com.cloudkeeper.leasing.identity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@ApiModel(value = "阵地使用情况人流量折线图VO", description = "阵地使用情况人流量折线图 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LinkChartVo {

    /** 阵地id*/
    @ApiModelProperty(value = "阵地id", position = 10, required = true)
    private String positionId;

    /** 每小时统计人流总数 */
    @ApiModelProperty(value = "每小时统计人流总数", position = 10, required = true)
    private Integer total;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 10, required = true)
    private LocalDateTime startTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 10, required = true)
    private LocalDateTime endTime;

    /** 阵地类型*/
    @ApiModelProperty(value = "阵地类型", position = 10, required = true)
    private String type;

    /** 所属组织*/
    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    private String districtId;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }
}
