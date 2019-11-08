package com.cloudkeeper.leasing.identity.dto.activityofficeprogress;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
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
 * 机关活动进度记录 DTO
 * @author asher
 */
@ApiModel(value = "机关活动进度记录 DTO", description = "机关活动进度记录 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ActivityOfficeProgressDTO extends BaseEditDTO {

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