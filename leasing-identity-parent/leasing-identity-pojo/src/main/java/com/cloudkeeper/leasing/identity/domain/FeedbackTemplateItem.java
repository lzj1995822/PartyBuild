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
 * 反馈配置项
 * @author asher
 */
@ApiModel(value = "反馈配置项", description = "反馈配置项")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.Feedback_Template_Item")
public class FeedbackTemplateItem extends BaseEntity {

    /** 材料名称 */
    @ApiModelProperty(value = "反馈项名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 材料编码 */
    @ApiModelProperty(value = "反馈项编码", position = 13, required = true)
    @Column(length = 60)
    private String code;

    /** 材料名称 */
    @ApiModelProperty(value = "反馈项类型", position = 15, required = true)
    @Column(length = 60)
    private String type;

    /** 模板id */
    @ApiModelProperty(value = "模板id", position = 17, required = true)
    @Column(length = 60)
    private String templateId;

}