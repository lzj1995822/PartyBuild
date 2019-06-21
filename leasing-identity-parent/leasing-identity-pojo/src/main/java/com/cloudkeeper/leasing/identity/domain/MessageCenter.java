package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.MessageCenterVO;
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
 * 消息中心
 * @author cqh
 */
@ApiModel(value = "消息中心", description = "消息中心")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Message_Center")
public class MessageCenter extends BaseEntity {

    /** 类型id */
    @ApiModelProperty(value = "类型Id", position = 10, required = true)
    @Column(length = 60)
    private String businessId;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    @Column(length = 60)
    private String type;

    /** id 组织*/
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 10, required = true)
    @Column(length = 60)
    private String content;

    /** 是否已读*/
    @ApiModelProperty(value = "是否已读", position = 10, required = true)
    @Column(length = 60)
    private Integer isRead;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    @Column(length = 60)
    private String title;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    @Column(length = 60)
    private String districtName;


}