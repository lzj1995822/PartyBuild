package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.base.enumeration.BooleanEnum;
import com.cloudkeeper.leasing.identity.domain.DistLearningActivityVideo;
import com.cloudkeeper.leasing.identity.domain.ParActivityReleaseFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityAllVO {
    @ApiModelProperty(value = "截止日期", position = 10, required = true)
    private LocalDate month;

    /** 背景 */
    @ApiModelProperty(value = "背景", position = 10, required = true)
    private String context;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String title;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    private String status;

    /** 区ID */
    @ApiModelProperty(value = "区ID", position = 10, required = true)
    private String districtID;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型", position = 10, required = true)
    private String taskType;

    /** 释放时间 */
    @ApiModelProperty(value = "释放时间", position = 10, required = true)
    private LocalDateTime releaseTime;

    /** 报警时间 */
    @ApiModelProperty(value = "报警时间", position = 10, required = true)
    private LocalDateTime alarmTime;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    private Integer score;

    /** 市完成进度 */
    @ApiModelProperty(value = "市完成进度", position = 10, required = true)
    private BigDecimal totalPercent;

    /** 下蜀 */
    @ApiModelProperty(value = "下蜀", position = 10, required = true)
    private BigDecimal xiaShuPercent;

    /** 宝华 */
    @ApiModelProperty(value = "宝华", position = 10, required = true)
    private BigDecimal baoHuaPercent;

    /** 茅山 */
    @ApiModelProperty(value = "茅山", position = 10, required = true)
    private BigDecimal maoShanPercent;

    /** 后白 */
    @ApiModelProperty(value = "后白", position = 10, required = true)
    private BigDecimal houBaiPercent;

    /** 白兔 */
    @ApiModelProperty(value = "白兔", position = 10, required = true)
    private BigDecimal baiTuPercent;

    /** 茅山风景区 */
    @ApiModelProperty(value = "茅山风景区", position = 10, required = true)
    private BigDecimal maoShanFengJingPercent;

    /** 边城 */
    @ApiModelProperty(value = "边城", position = 10, required = true)
    private BigDecimal bianChengPercent;

    /** 郭庄 */
    @ApiModelProperty(value = "郭庄", position = 10, required = true)
    private BigDecimal guoZhuangPercent;

    /** 华阳 */
    @ApiModelProperty(value = "华阳", position = 10, required = true)
    private BigDecimal huaYangPercent;

    /** 开发区 */
    @ApiModelProperty(value = "开发区", position = 10, required = true)
    private BigDecimal kaiFaPercent;

    /** 天王 */
    @ApiModelProperty(value = "天王", position = 10, required = true)
    private BigDecimal tianWangPercent;

    @ApiModelProperty(value="任务对象类型",position = 10,required = true)
    private String objectType;
}
