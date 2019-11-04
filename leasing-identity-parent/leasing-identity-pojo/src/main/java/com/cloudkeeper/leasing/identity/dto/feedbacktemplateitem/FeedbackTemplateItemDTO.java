package com.cloudkeeper.leasing.identity.dto.feedbacktemplateitem;

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
 * 反馈配置项 DTO
 * @author asher
 */
@ApiModel(value = "反馈配置项 DTO", description = "反馈配置项 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackTemplateItemDTO extends BaseEditDTO {

    /** 反馈项名称 */
    @ApiModelProperty(value = "反馈项名称", position = 10, required = true)
    private String name;

    /** 反馈项编码 */
    @ApiModelProperty(value = "反馈项编码", position = 13, required = true)
    private String code;

    /** 反馈项类型 */
    @ApiModelProperty(value = "反馈项类型", position = 15, required = true)
    private String type;

    /** 模板id */
    @ApiModelProperty(value = "模板id", position = 17, required = true)
    private String templateId;

}