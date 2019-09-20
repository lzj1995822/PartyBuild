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
import java.util.List;

/**
 * 手机截图
 * @author lxw
 */
@ApiModel(value = "手机截图", description = "手机截图")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_ActivityPicture")
public class ParActivityPicture extends BaseEntity {

    /** feedbackID */
    @ApiModelProperty(value = "feedbackID", position = 10, required = true)
    @Column(length = 60)
    private String activityID;

    /** 图片地址 */
    @ApiModelProperty(value = "图片地址", position = 10, required = true)
    @Column(length = 60)
    private String ImageUrl;


    /** 活动记录 */
    @ApiModelProperty(value = "活动", position = 28)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "activityID", insertable = false, updatable = false)
    private ParActivityFeedback parActivityFeedback;


    /** redis图片缓存id */
    @ApiModelProperty(value = "redis图片缓存id", position = 10, required = true)
    private String redisUuid;


}
