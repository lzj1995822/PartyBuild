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
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    /** 截止日期 */
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

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10, required = true)
    @JsonIgnore
    @OneToMany(mappedBy = "parActivity")
    private List<ParActivityReleaseFile> parActivityReleaseFile;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        ParActivityVO parActivityVO = (ParActivityVO) convert;
        if(!StringUtils.isEmpty(this.parActivityReleaseFile)){
            parActivityVO.setUrls(this.parActivityReleaseFile);
        }
        return (T) parActivityVO;
    }

}
