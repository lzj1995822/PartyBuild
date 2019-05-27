package com.cloudkeeper.leasing.identity.dto.information;

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
 * 消息通知 DTO
 * @author cqh
 */
@ApiModel(value = "消息通知 DTO", description = "消息通知 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class InformationDTO extends BaseEditDTO {

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

}