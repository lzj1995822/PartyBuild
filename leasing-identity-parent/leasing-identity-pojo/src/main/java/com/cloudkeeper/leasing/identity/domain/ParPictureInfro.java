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
import java.time.LocalDateTime;

/**
 * 电视截图
 * @author lxw
 */
@ApiModel(value = "电视截图", description = "电视截图")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_Picture_Infro")
public class ParPictureInfro extends BaseEntity {

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    @Column(name = "CreateTime",length = 60)
    private LocalDateTime createTime;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String organizationId;

    /** 图片地址 */
    @ApiModelProperty(value = "图片地址", position = 10, required = true)
    @Column(name = "ImageURL",length = 60)
    private String imageURL;

    /** 任务ID */
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    @Column(name = "StudyContent",length = 60)
    private String studyContent;

    /** 数据库图片缓存的id */
    @ApiModelProperty(value = "数据库图片缓存的id", position = 12)
    private String redisUuid;

}
