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
 * 反馈配置项值
 * @author asher
 */
@ApiModel(value = "反馈配置项值", description = "反馈配置项值")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.Feedback_Item_Value")
public class FeedbackItemValue extends BaseEntity {

    /** 活动指派表id */
    @ApiModelProperty(value = "活动指派表id", position = 10, required = true)
    private String objectId;

    /** 关联的配置项id */
    @ApiModelProperty(value = "关联的配置项id", position = 10, required = true)
    private String itemId;

    /** 值的类型 */
    @ApiModelProperty(value = "值的类型", position = 10, required = true)
    private String type;

    /** 值 */
    @ApiModelProperty(value = "值", position = 10, required = true)
    private String value;

    /** 配置项名称 */
    @ApiModelProperty(value = "配置项名称", position = 10, required = true)
    private String name;

    /** code */
    @ApiModelProperty(value = "code", position = 10, required = true)
    private String code;

}