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
 * 消息中心 VO
 * @author cqh
 */
@ApiModel(value = "消息中心 VO", description = "消息中心 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MessageCenterVO extends BaseVO {

    /** 类型Id */
    @ApiModelProperty(value = "类型Id", position = 10, required = true)
    private String businessId;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    private String districtId;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 10, required = true)
    private String content;

    /** 是否已读 */
    @ApiModelProperty(value = "是否已读", position = 10, required = true)
    private Integer isRead;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 10, required = true)
    private String title;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 10, required = true)
    private String districtName;
}