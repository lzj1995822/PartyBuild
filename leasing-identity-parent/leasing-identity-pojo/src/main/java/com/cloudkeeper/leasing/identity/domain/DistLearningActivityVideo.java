package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 远教视频
 * @author lxw
 */
@ApiModel(value = "远教视频", description = "远教视频")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.DistLearning_ActivityVideo")
public class DistLearningActivityVideo extends BaseEntity {

    /** 活动ID */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    @Column(length = 60)
    private String activityId;

    /** 视频名字 */
    @ApiModelProperty(value = "视频名字", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 视频地址 */
    @ApiModelProperty(value = "视频地址", position = 10, required = true)
    @Column(length = 60)
    private String videoUrl;

    /** 视频封面 */
    @ApiModelProperty(value = "视频封面", position = 10, required = true)
    @Column(length = 60)
    private String videoCover;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10, required = true)
    @Column(length = 60)
    private String remark;

    /** 时长 */
    @ApiModelProperty(value = "时长", position = 10, required = true)
    @Column(length = 60)
    private String lengthOfTime;

    /** 活动 */
    @ApiModelProperty(value = "活动", position = 28)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "activityId", insertable = false, updatable = false)
    private ParActivity parActivity;

}
