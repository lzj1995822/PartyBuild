package com.cloudkeeper.leasing.identity.dto.sysconfiguration;

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
 * 系统属性配置 查询DTO
 * @author cqh
 */
@ApiModel(value = "系统属性配置 查询DTO", description = "系统属性配置 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysConfigurationSearchable extends BaseSearchable {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

    /** 名称code */
    @ApiModelProperty(value = "名称code", position = 10, required = true)
    private String code;

    /** 值 */
    @ApiModelProperty(value = "值", position = 10, required = true)
    private String codeValue;

}