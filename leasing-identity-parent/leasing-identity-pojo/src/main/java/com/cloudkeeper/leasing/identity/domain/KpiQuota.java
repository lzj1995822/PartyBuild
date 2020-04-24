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
 * 村主任考核指标
 * @author yujian
 */
@ApiModel(value = "村主任考核指标", description = "村主任考核指标")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.KPI_Quota")
public class KpiQuota extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "指标名称", position = 10, required = true)
    @Column(length = 60)
    private String quotaName;

    /** 名称 */
    @ApiModelProperty(value = "父指标ID", position = 10, required = true)
    @Column(length = 60)
    private String parentQuotaId;

    @ApiModelProperty(value = "指标标识", position = 10, required = true)
    @Column(length = 60)
    private String quotaId;
    @ApiModelProperty(value = "年度", position = 10, required = true)
    @Column(length = 60)
    private String quotaYear;

}
