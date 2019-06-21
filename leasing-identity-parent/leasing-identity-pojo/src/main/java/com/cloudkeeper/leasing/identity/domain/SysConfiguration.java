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
 * 系统属性配置
 * @author cqh
 */
@ApiModel(value = "系统属性配置", description = "系统属性配置")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Sys_Configuration")
public class SysConfiguration extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 名称code */
    @ApiModelProperty(value = "名称code", position = 10, required = true)
    @Column(length = 60)
    private String code;

    /** 值 */
    @ApiModelProperty(value = "值", position = 10, required = true)
    @Column(length = 60)
    private String codeValue;

}