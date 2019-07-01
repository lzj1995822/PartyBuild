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

}