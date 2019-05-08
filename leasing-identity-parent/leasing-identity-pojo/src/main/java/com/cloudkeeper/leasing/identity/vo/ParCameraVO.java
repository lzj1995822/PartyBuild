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
 * 监控信息 VO
 * @author cqh
 */
@ApiModel(value = "监控信息 VO", description = "监控信息 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParCameraVO extends BaseVO {

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 10, required = true)
    private String organizationId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 10, required = true)
    private String name;

    /** IP通道地址 */
    @ApiModelProperty(value = "IP通道地址", position = 10, required = true)
    private String IP;

    /** 机顶盒序列号 */
    @ApiModelProperty(value = "机顶盒序列号", position = 10, required = true)
    private String number;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 时间 */
    @ApiModelProperty(value = "时间", position = 10, required = true)
    private String time;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10, required = true)
    private String remark;

}