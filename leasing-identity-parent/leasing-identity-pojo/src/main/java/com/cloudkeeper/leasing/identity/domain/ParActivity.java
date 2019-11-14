package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 活动
 * @author lxw
 */
@ApiModel(value = "活动", description = "活动")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_Activity")
public class ParActivity extends BaseEntity {

    /** 截止日期 */
    @ApiModelProperty(value = "截止日期", position = 10, required = true)
    @Column(length = 60)
    private LocalDate month;

    /** 背景 */
    @ApiModelProperty(value = "背景", position = 10, required = true)
    @Column(length = 60)
    private String context;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String title;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    @Column(length = 60)
    private String status;

    /** 区ID */
    @ApiModelProperty(value = "区ID", position = 10, required = true)
    @Column(length = 60)
    private String districtID;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    @Column(length = 60)
    private String type;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型", position = 10, required = true)
    @Column(length = 60)
    private String taskType;

    /** 释放时间 */
    @ApiModelProperty(value = "释放时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime releaseTime;

    /** 报警时间 */
    @ApiModelProperty(value = "报警时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime alarmTime;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    @Column(length = 60)
    private Integer score;

    /** 模板id */
    @ApiModelProperty(value = "模板id", position = 10, required = true)
    @Column(length = 60)
    private String templateId;

    /** 市完成进度 */
    @ApiModelProperty(value = "市完成进度", position = 10, required = true)
    private BigDecimal totalPercent = BigDecimal.ZERO;

    /** 下蜀 */
    @ApiModelProperty(value = "下蜀", position = 10, required = true)
    private BigDecimal xiaShuPercent = BigDecimal.ZERO;

    /** 宝华 */
    @ApiModelProperty(value = "宝华", position = 10, required = true)
    private BigDecimal baoHuaPercent = BigDecimal.ZERO;

    /** 茅山 */
    @ApiModelProperty(value = "茅山", position = 10, required = true)
    private BigDecimal maoShanPercent = BigDecimal.ZERO;

    /** 后白 */
    @ApiModelProperty(value = "后白", position = 10, required = true)
    private BigDecimal houBaiPercent = BigDecimal.ZERO;

    /** 白兔 */
    @ApiModelProperty(value = "白兔", position = 10, required = true)
    private BigDecimal baiTuPercent = BigDecimal.ZERO;

    /** 茅山风景区 */
    @ApiModelProperty(value = "茅山风景区", position = 10, required = true)
    private BigDecimal maoShanFengJingPercent = BigDecimal.ZERO;

    /** 边城 */
    @ApiModelProperty(value = "边城", position = 10, required = true)
    private BigDecimal bianChengPercent = BigDecimal.ZERO;

    /** 郭庄 */
    @ApiModelProperty(value = "郭庄", position = 10, required = true)
    private BigDecimal guoZhuangPercent = BigDecimal.ZERO;

    /** 华阳 */
    @ApiModelProperty(value = "华阳", position = 10, required = true)
    private BigDecimal huaYangPercent = BigDecimal.ZERO;

    /** 开发区 */
    @ApiModelProperty(value = "开发区", position = 10, required = true)
    private BigDecimal kaiFaPercent = BigDecimal.ZERO;

    /** 天王 */
    @ApiModelProperty(value = "天王", position = 10, required = true)
    private BigDecimal tianWangPercent = BigDecimal.ZERO;
    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10, required = true)
    @JsonIgnore
    @OneToMany(mappedBy = "parActivity")
    private List<ParActivityReleaseFile> parActivityReleaseFile;

    /** 视频 */
    @ApiModelProperty(value = "附件", position = 10, required = true)
    @JsonIgnore
    @OneToMany(mappedBy = "parActivity")
    private List<DistLearningActivityVideo> distLearningActivityVideo;

    @ApiModelProperty(value="任务对象类型",position = 10,required = true)
    @Column(length = 60)
    private String objectType;

    @ApiModelProperty(value="所需上传的材料的名字拼接",position = 10)
    @Column(length = 255)
    private String templateItem;

    @ApiModelProperty(value="二级审核党组织进度",position = 10)
    @OneToMany(mappedBy = "parActivity")
    private List<ActivityOfficeProgress> activityOfficeProgresses;


    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        ParActivityVO parActivityVO = (ParActivityVO) convert;
        if(!StringUtils.isEmpty(this.parActivityReleaseFile)){
            parActivityVO.setUrls(this.parActivityReleaseFile);
        }
        if(!StringUtils.isEmpty(this.distLearningActivityVideo)){
            parActivityVO.setVideo(this.distLearningActivityVideo);
        }
        if(!StringUtils.isEmpty(activityOfficeProgresses)){
            Map<String, BigDecimal> collect = this.activityOfficeProgresses.stream().collect(Collectors.toMap(ActivityOfficeProgress::getDistrictId, ActivityOfficeProgress::getPercent));
            parActivityVO.setActivityOfficeProgresses(collect);
        }
        return (T) parActivityVO;
    }

}
