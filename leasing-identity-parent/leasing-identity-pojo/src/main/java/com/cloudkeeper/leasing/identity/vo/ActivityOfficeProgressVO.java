package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 机关活动进度记录 VO
 * @author asher
 */
@ApiModel(value = "机关活动进度记录 VO", description = "机关活动进度记录 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ActivityOfficeProgressVO extends BaseVO {

    /** 活动id */
    @ApiModelProperty(value = "活动id", position = 10, required = true)
    private String activityId;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 13)
    private String districtId;

    /** 进度 */
    @ApiModelProperty(value = "进度", position = 17)
    private BigDecimal percent;

}