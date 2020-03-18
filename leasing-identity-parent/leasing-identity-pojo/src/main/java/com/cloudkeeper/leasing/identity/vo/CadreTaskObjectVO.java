package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * 村书记模块发布任务对象记录 VO
 * @author asher
 */
@ApiModel(value = "村书记模块发布任务对象记录 VO", description = "村书记模块发布任务对象记录 VO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CadreTaskObjectVO extends BaseVO {

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
    @ApiModelProperty(value = "任务对象名称", position = 10, required = true)
    private String objectName;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 10, required = true)
    private String taskName;

    /** 镇名 */
    @ApiModelProperty(value = "镇名", position = 10, required = true)
    private String townName;

    /** 村書記更新任務詳情 */
    @ApiModelProperty(value = "村書記更新任務詳情", position = 10, required = true)
    private List<VillageCadresInfoVO> villageCadres;

    /** 当前对象进度 */
    @ApiModelProperty(value = "当前对象进度")
    private String currentPercent;

    private String taskType;

    private LocalDate endTime;
}
