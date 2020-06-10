package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 村书记模块任务
 * @author asher
 */
@ApiModel(value = "村书记模块任务", description = "村书记模块任务")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cadre_task")
public class CadreTask extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称")
    private String name;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型")
    private String type;

    /** 得分 */
    @ApiModelProperty(value = "得分")
    private String score;

    /** 截止时间 */
    @ApiModelProperty(value = "截止时间")
    private LocalDate endTime;

    /** 内容 */
    @ApiModelProperty(value = "内容")
    private String content;

    /** 附件 */
    @ApiModelProperty(value = "附件")
    private String attach;

    /** 市总进度 */
    @ApiModelProperty(value = "市总进度")
    private String currentPercent;

    /** 年度 */
    @ApiModelProperty(value = "年度")
    private String taskYear;

    /** 季度 */
    @ApiModelProperty(value = "季度")
    private String taskQuarter;

    /** 成果相关文件 */
    @ApiModelProperty(value = "成果相关文件")
    private String resultFiles;

    /** 任务所属模块 */
    @ApiModelProperty(value = "任务所属模块")
    private String taskModule;

    /** 是否生成考核结果 */
    @ApiModelProperty(value = "是否生成考核结果")
    private String hasGenerateResult;

    /** 是否可以生成考核结果 */
    @ApiModelProperty(value = "是否可以生成考核结果")
    private String generateResultEnable;

    /** 指标制定任务是否经过确认了 */
    @ApiModelProperty(value = "指标制定任务是否经过确认了")
    private String hasConfirm = "0";

}