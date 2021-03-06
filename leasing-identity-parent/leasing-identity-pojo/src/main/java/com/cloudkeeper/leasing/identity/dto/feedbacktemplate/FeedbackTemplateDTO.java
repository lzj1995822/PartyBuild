package com.cloudkeeper.leasing.identity.dto.feedbacktemplate;

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
 * 反馈配置模板 DTO
 * @author asher
 */
@ApiModel(value = "反馈配置模板 DTO", description = "反馈配置模板 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackTemplateDTO extends BaseEditDTO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

}