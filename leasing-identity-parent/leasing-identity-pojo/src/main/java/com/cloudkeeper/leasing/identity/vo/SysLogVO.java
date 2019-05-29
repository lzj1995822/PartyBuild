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
 * 系统日志 VO
 * @author asher
 */
@ApiModel(value = "系统日志 VO", description = "系统日志 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysLogVO extends BaseVO {

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

}