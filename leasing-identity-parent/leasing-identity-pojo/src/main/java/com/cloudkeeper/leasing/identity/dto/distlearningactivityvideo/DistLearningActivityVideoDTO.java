package com.cloudkeeper.leasing.identity.dto.distlearningactivityvideo;

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
 * 远教视频 DTO
 * @author lxw
 */
@ApiModel(value = "远教视频 DTO", description = "远教视频 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DistLearningActivityVideoDTO extends BaseEditDTO {

    /** 活动ID */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    private String activityId;

    /** 视频名字 */
    @ApiModelProperty(value = "视频名字", position = 10, required = true)
    private String name;

    /** 视频地址 */
    @ApiModelProperty(value = "视频地址", position = 10, required = true)
    private String videoUrl;

    /** 视频封面 */
    @ApiModelProperty(value = "视频封面", position = 10, required = true)
    private String videoCover;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10, required = true)
    private String remark;

    /** 时长 */
    @ApiModelProperty(value = "时长", position = 10, required = true)
    private String lengthOfTime;

}
