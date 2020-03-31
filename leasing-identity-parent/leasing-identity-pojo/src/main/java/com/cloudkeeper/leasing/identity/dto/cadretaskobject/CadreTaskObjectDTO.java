package com.cloudkeeper.leasing.identity.dto.cadretaskobject;

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
 * 村书记模块发布任务对象记录 DTO
 * @author asher
 */
@ApiModel(value = "村书记模块发布任务对象记录 DTO", description = "村书记模块发布任务对象记录 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CadreTaskObjectDTO extends BaseEditDTO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String taskId;

    /** 任务对象Id */
    @ApiModelProperty(value = "任务对象Id", position = 10, required = true)
    private String objectId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 对象类型 */
    @ApiModelProperty(value = "对象类型", position = 10, required = true)
    private String objectType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10, required = true)
    private String note;

    /** 任务对象名称 */
    @ApiModelProperty(value = "任务对象Id", position = 10, required = true)
    private String objectName;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 10, required = true)
    private String taskName;

    /** 镇名 */
    @ApiModelProperty(value = "镇名", position = 10, required = true)
    private String townName;

    /** 成果相关文件 */
    @ApiModelProperty(value = "成果相关文件")
    private String resultFiles;

    /** 个人申请 */
    @ApiModelProperty(value = "个人申请")
    private String applyFiles;

    /** 考察审查文件 */
    @ApiModelProperty(value = "考察审查文件")
    private String reviewFiles;

    /** 获奖证书 */
    @ApiModelProperty(value = "获奖证书")
    private String rewardFiles;

    /** 最新审核人 */
    @ApiModelProperty(value = "最新审核人")
    private String lastestAuditor;

    /** 最新审核意见 */
    @ApiModelProperty(value = "最新审核意见")
    private String lastestAdvice;
}