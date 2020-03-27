package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 综合评议 VO
 * @author yujian
 */
@ApiModel(value = "综合评议 VO", description = "综合评议 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KPIEvaluationVO extends BaseVO {

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 任务ID */
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    private String taskId;

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 10, required = true)
    private String indexNum;

    /** 项目类别 */
    @ApiModelProperty(value = "项目类别", position = 10, required = true)
    private String commentProject;

    /** 项目内容 */
    @ApiModelProperty(value = "项目内容", position = 10, required = true)
    private String commentContent;

    /** 满意 */
    @ApiModelProperty(value = "满意", position = 10, required = true)
    private int good;

    /** 比较满意 */
    @ApiModelProperty(value = "比较满意", position = 10, required = true)
    private int preferably;

    /** 基本满意 */
    @ApiModelProperty(value = "基本满意", position = 10, required = true)
    private int commonly;

    /** 不满意 */
    @ApiModelProperty(value = "不满意", position = 10, required = true)
    private int bad;

}