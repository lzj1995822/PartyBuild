package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 系统用户
 * @author asher
 */
@ApiModel(value = "系统用户", description = "系统用户")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.SYS_User")
public class SysUser extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 12, required = true)
    @Column(length = 60)
    private String userName;

    /** 密码 */
    @ApiModelProperty(value = "密码", position = 14, required = true)
    @Column(length = 60)
    private String password;

    /** 角色 */
    @ApiModelProperty(value = "角色", position = 16, required = true)
    @Column(length = 60)
    private String roleId;

    /** 是否可用 */
    @ApiModelProperty(value = "是否可用", position = 18, required = true)
    private Integer enable;

    /** XX */
    @ApiModelProperty(value = "XXX", position = 20, required = true)
    private String portrait;

    /** XX */
    @ApiModelProperty(value = "XX", position = 22, required = true)
    private LocalDateTime lastTime;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 24, required = true)
    private String organizationId;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 26, required = true)
    private String phone;




}