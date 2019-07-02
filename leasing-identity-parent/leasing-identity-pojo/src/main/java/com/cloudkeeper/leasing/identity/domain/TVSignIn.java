package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.TVSignInVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * 远教视频签到记录
 * @author cqh
 */
@ApiModel(value = "远教视频签到记录", description = "远教视频签到记录")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TV_Sign_In")
public class TVSignIn extends BaseEntity {

    /** 活动Id */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String activityId;

    /** 组织Id */
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    @Column(length = 60)
    private String organizationId;

    /** 视频Id */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String videoId;

    /** 签到人员 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String signInRecord;

    /** 签退人员 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String signOutRecord;

    /** 是否看完 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private Integer flag;

    /** 视频*/
    @ApiModelProperty(value = "视频", position = 24)
    @ManyToOne
    @JoinColumn(name = "videoId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private DistLearningActivityVideo distLearningActivityVideo;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        TVSignInVO tvSignInVO = (TVSignInVO) convert;
        if(!StringUtils.isEmpty(this.distLearningActivityVideo)){
            tvSignInVO.setVideoName(this.distLearningActivityVideo.getName());
            tvSignInVO.setVideoCover(this.distLearningActivityVideo.getVideoCover());
            tvSignInVO.setLengthOfTime(this.distLearningActivityVideo.getLengthOfTime());
            tvSignInVO.setVideoUrl(this.distLearningActivityVideo.getVideoUrl());
        }
        return (T) tvSignInVO;
    }


}