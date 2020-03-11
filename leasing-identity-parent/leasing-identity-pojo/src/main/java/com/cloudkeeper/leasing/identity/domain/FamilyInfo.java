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
 * 专职村书记家庭情况
 * @author yujian
 */
@ApiModel(value = "专职村书记家庭情况", description = "专职村书记家庭情况")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.family_info")
public class FamilyInfo extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String name;
    @ApiModelProperty(value = "称谓", position = 10, required = true)
    @Column(length = 60)
    private String appellation;

    @ApiModelProperty(value = "出生日期", position = 10, required = true)
    @Column(length = 60)
    private String birthDay;

    @ApiModelProperty(value = "政治面貌", position = 10, required = true)
    @Column(length = 60)
    private String politicalStatus;

    @ApiModelProperty(value = "作单位及职务", position = 10, required = true)
    @Column(length = 60)
    private String orgAndJob;

    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    @Column(length = 60)
    private String cadresId;

}