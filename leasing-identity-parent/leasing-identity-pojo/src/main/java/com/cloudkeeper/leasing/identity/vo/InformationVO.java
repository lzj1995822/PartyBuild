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
 * 消息通知 VO
 * @author cqh
 */
@ApiModel(value = "消息通知 VO", description = "消息通知 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class InformationVO extends BaseVO {

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    private String title;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 10, required = true)
    private String description;

    /** 发布时间 */
    @ApiModelProperty(value = "发布时间", position = 10, required = true)
    private LocalDateTime releaseTime;

    /** 发布对象 */
    @ApiModelProperty(value = "发布对象", position = 10, required = true)
    private String districtID;

    /** 发布人 */
    @ApiModelProperty(value = "发布人", position = 10, required = true)
    private String name;

}