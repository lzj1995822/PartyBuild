package com.cloudkeeper.leasing.identity.dto.sysuser;

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
 * 系统用户 DTO
 * @author asher
 */
@ApiModel(value = "系统用户 DTO", description = "系统用户 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDTO extends BaseEditDTO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 12, required = true)
    private String userName;

    /** 密码 */
    @ApiModelProperty(value = "密码", position = 14, required = true)
    private String password;

    /** 角色 */
    @ApiModelProperty(value = "角色", position = 16, required = true)
    private String roleID;

    /** 是否可用 */
    @ApiModelProperty(value = "是否可用", position = 18, required = true)
    private Integer enable;

    /** XXX */
    @ApiModelProperty(value = "XXX", position = 20, required = true)
    private String portrait;

    /** XX */
    @ApiModelProperty(value = "XX", position = 22, required = true)
    private LocalDateTime lastTime;

    /** 头像 */
    @ApiModelProperty(value = "头像", position = 22, required = true)
    private String image;

    /** 组织简介 */
    @ApiModelProperty(value = "组织简介", position = 22, required = true)
    private String introduction;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 24, required = true)
    private String organizationId;

    /** 组织districtId */
    @ApiModelProperty(value = "组织districtId", position = 24, required = true)
    private String districtId;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 26, required = true)
    private String phone;

    @ApiModelProperty(value = "是否是手机端", position = 16, required = true)
    private int isMobile;

}