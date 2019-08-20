package com.cloudkeeper.leasing.identity.dto.paractivityobject;

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
import java.util.List;

/**
 * 任务对象 DTO
 * @author lxw
 */
@ApiModel(value = "任务对象 DTO", description = "任务对象 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityObjectDTO extends BaseEditDTO {

    /** 活动Id */
    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    private String activityId;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String organizationId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 是否在用 */
    @ApiModelProperty(value = "是否在用", position = 10, required = true)
    private String isWorking;

    /** 关联组织 */
    @ApiModelProperty(value = "关联组织", position = 10, required = true)
    private String attachTo;

    /** 摄像头Number */
    @ApiModelProperty(value = "摄像头Number", position = 10, required = true)
    private String number;

    /**判断是否手机或电视端执行 */
    @ApiModelProperty(value = "执行方法", position = 10, required = true)
    private String phoneOrTv;

    /**用户ID */
    @ApiModelProperty(value = "用户ID", position = 10, required = true)
    private String userId;

    /**手机图片*/
    @ApiModelProperty(value = "手机图片",position = 10,required = true)
    private List<String> phoneImgList;

    /** 任务对象类型 */
    @ApiModelProperty(value = "任务对象类型", position = 10, required = true)
    private String objectType;

}
