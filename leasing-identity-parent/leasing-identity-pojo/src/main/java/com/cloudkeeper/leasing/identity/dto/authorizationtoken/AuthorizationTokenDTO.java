package com.cloudkeeper.leasing.identity.dto.authorizationtoken;

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
 * 访问权限 DTO
 * @author cqh
 */
@ApiModel(value = "访问权限 DTO", description = "访问权限 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationTokenDTO extends BaseEditDTO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

    /** code */
    @ApiModelProperty(value = "code", position = 10, required = true)
    private String code;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 10, required = true)
    private String des;

    /** 值 */
    @ApiModelProperty(value = "值", position = 10, required = true)
    private String tokenVal;

    /** 是否使用 0不 1使用 */
    @ApiModelProperty(value = "是否使用", position = 10, required = true)
    private String isUse;

}