package com.cloudkeeper.leasing.identity.dto.paractivityfeedback;

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
 * 移动端执行记录 DTO
 * @author lxw
 */
@ApiModel(value = "移动端执行记录 DTO", description = "移动端执行记录 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityFeedbackDTO extends BaseEditDTO {

    /** 活动ID */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    private String snId;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 10, required = true)
    private String userId;


    /** 描述 */
    @ApiModelProperty(value = "描述", position = 10, required = true)
    private String context;

    /** 上传时间 */
    @ApiModelProperty(value = "上传时间", position = 10, required = true)
    private LocalDateTime time;

    /** 标记 */
    @ApiModelProperty(value = "标记", position = 10, required = true)
    private String flag;

}
