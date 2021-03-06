package com.cloudkeeper.leasing.identity.dto.cadretask;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
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
 * 村书记模块任务 查询DTO
 * @author asher
 */
@ApiModel(value = "村书记模块任务 查询DTO", description = "村书记模块任务 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CadreTaskSearchable extends BaseSearchable {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 0)
    private String name;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型", position = 0)
    private String type;

    /** 得分 */
    @ApiModelProperty(value = "得分", position = 0)
    private String score;

    /** 截止时间 */
    @ApiModelProperty(value = "截止时间", position = 0)
    private LocalDate endTime;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 0)
    private String content;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 0)
    private String attach;

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
    private String hasConfirm;
}