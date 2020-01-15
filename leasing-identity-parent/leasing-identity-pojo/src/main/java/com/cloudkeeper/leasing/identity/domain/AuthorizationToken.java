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

/**
 * 访问权限
 * @author cqh
 */
@ApiModel(value = "访问权限", description = "访问权限")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorization_token")
public class AuthorizationToken extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** code */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String code;

    /** 描述 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String des;

    /** 值 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String tokenVal;

}