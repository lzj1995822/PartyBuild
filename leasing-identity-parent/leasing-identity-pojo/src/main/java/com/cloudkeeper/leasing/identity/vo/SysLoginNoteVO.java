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
 * 系统登录日志 VO
 * @author cqh
 */
@ApiModel(value = "系统登录日志 VO", description = "系统登录日志 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysLoginNoteVO extends BaseVO {

    /** 登录时间 */
    @ApiModelProperty(value = "登录时间", position = 10, required = true)
    private LocalDateTime createTime;

    /** 登录名 */
    @ApiModelProperty(value = "登录名", position = 10, required = true)
    private String userName;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 10, required = true)
    private String userId;

    /** 操作 */
    @ApiModelProperty(value = "操作", position = 10, required = true)
    private String action;

    /** 地区 */
    @ApiModelProperty(value = "地区", position = 10, required = true)
    private String region;

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", position = 10, required = true)
    private String name;

}