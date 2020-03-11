package com.cloudkeeper.leasing.identity.dto.cadretask;

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
 * 村书记模块任务 DTO
 * @author asher
 */
@ApiModel(value = "村书记模块任务 DTO", description = "村书记模块任务 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CadreTaskDTO extends BaseEditDTO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 0)
    private String name;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型", position = 0)
    private String type;

    /** 得分 */
    @ApiModelProperty(value = "得分", position = 0)
    private String score;

    /** 截止时间 */
    @ApiModelProperty(value = "截止时间", position = 0)
    private LocalDate endTime;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 0)
    private String content;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 0)
    private String attach;

}