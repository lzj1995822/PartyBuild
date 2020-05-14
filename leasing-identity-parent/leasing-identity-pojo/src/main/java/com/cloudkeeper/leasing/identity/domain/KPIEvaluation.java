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
 * 综合评议
 * @author yujian
 */
@ApiModel(value = "综合评议", description = "综合评议")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.KPI_Evaluation")
public class KPIEvaluation extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    @Column(length = 60)
    private String type;
    /** 名称 */
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    @Column(length = 60)
    private String taskId;
    /** 名称 */
    @ApiModelProperty(value = "序号", position = 10, required = true)
    @Column(length = 60)
    private String indexNum;
    /** 名称 */
    @ApiModelProperty(value = "项目类别", position = 10, required = true)
    @Column(length = 60)
    private String commentProject;

    /** 名称 */
    @ApiModelProperty(value = "项目内容", position = 10, required = true)
    @Column(length = 60)
    private String commentContent;

    /** 名称 */
    @ApiModelProperty(value = "满意", position = 10, required = true)
    @Column(length = 60)
    private Integer good;

    /** 名称 */
    @ApiModelProperty(value = "比较满意", position = 10, required = true)
    @Column(length = 60)
    private Integer preferably;

    /** 名称 */
    @ApiModelProperty(value = "基本满意", position = 10, required = true)
    @Column(length = 60)
    private Integer commonly;

    /** 名称 */
    @ApiModelProperty(value = "不满意", position = 10, required = true)
    @Column(length = 60)
    private Integer bad;

    /** 村名 */
    @ApiModelProperty(value = "村名", position = 10, required = true)
    private String districtId;

    /** 关联到村的指标记录id */
    @ApiModelProperty(value = "关联到村的指标记录id", position = 10, required = true)
    private String villageQuotaId;
}
