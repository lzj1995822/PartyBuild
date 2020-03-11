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
 * 专职村书记家庭工作情况
 * @author yujian
 */
@ApiModel(value = "专职村书记家庭工作情况", description = "专职村书记家庭工作情况")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.famliy_work_info")
public class FamilyWorkInfo extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    @ApiModelProperty(value = "称谓", position = 10, required = true)
    @Column(length = 60)
    private String appellation;

    @ApiModelProperty(value = "政治面貌", position = 10, required = true)
    @Column(length = 60)
    private String politicalStatus;

    @ApiModelProperty(value = "职务", position = 10, required = true)
    @Column(length = 60)
    private String job;

    @ApiModelProperty(value = "在村工作时长", position = 10, required = true)
    @Column(length = 60)
    private String duration;

    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    @Column(length = 60)
    private String cadresId;

}