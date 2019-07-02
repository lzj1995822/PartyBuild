package com.cloudkeeper.leasing.identity.dto.tvsignin;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
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
 * 远教视频签到记录 查询DTO
 * @author cqh
 */
@ApiModel(value = "远教视频签到记录 查询DTO", description = "远教视频签到记录 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TVSignInSearchable extends BaseSearchable {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String activityId;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    private String organizationId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String videoId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String signInRecord;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String signOutRecord;

}