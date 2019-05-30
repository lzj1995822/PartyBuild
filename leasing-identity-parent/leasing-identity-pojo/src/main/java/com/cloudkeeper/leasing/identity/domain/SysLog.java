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
 * 系统日志
 * @author asher
 */
@ApiModel(value = "系统日志", description = "系统日志")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SYS_Log")
public class SysLog extends BaseEntity {

    /** 控制器名称 */
    @ApiModelProperty(value = "控制器名称", position = 10, required = true)
    private String controllerName;

    /** 日志操作内容 */
    @ApiModelProperty(value = "日志操作内容", position = 10, required = true)
    private String msg;

    /** 涉及表名称 */
    @ApiModelProperty(value = "涉及表名称", position = 10, required = true)
    private String tableName;

    /** 涉及业务id */
    @ApiModelProperty(value = "涉及业务id", position = 10, required = true)
    private String bussinessId;

    /** 操作人 */
    @ApiModelProperty(value = "操作人", position = 10, required = true)
    private String actor;

}
