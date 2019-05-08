package com.cloudkeeper.leasing.identity.dto.sysloginlog;

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
 * 登录日志 DTO
 * @author lxw
 */
@ApiModel(value = "登录日志 DTO", description = "登录日志 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysLoginLogDTO extends BaseEditDTO {

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 10, required = true)
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 10, required = true)
    private String userName;


    /** 登陆时间 */
    @ApiModelProperty(value = "登陆时间", position = 14, required = true)
    private LocalDateTime loginTime;

    /** 登录成功 */
    @ApiModelProperty(value = "登录成功", position = 16, required = true)
    private String success;

}