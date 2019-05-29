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
 * 发布任务上传文件
 * @author lxw
 */
@ApiModel(value = "发布任务上传文件", description = "发布任务上传文件")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_ActivityReleaseFile")
public class ParActivityReleaseFile extends BaseEntity {

    /** 活动Id */
    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    @Column(length = 60)
    private String activityID;

    /** 文件地址 */
    @ApiModelProperty(value = "文件地址", position = 10, required = true)
    @Column(length = 60)
    private String Url;

    /** 活动 */
    @ApiModelProperty(value = "活动", position = 28)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "activityID", insertable = false, updatable = false)
    private ParActivity parActivity;


}
