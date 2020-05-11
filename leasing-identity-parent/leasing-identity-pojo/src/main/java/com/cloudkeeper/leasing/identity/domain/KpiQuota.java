package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

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
    private String quotaName;

    /** 名称 */
    @ApiModelProperty(value = "父指标ID", position = 10, required = true)
    private String parentQuotaId;

    @ApiModelProperty(value = "指标标识", position = 10, required = true)
    private String quotaId;

    @ApiModelProperty(value = "年度", position = 10, required = true)
    private String quotaYear;

    @ApiModelProperty(value = "指标层级", position = 10, required = true)
    private String quotaLevel;

    @ApiModelProperty(value = "指标分值", position = 10, required = true)
    private String quotaScore;

    @ApiModelProperty(value = "指标是否需要设置权重", position = 10, required = true)
    private String isSetWeight;

    @ApiModelProperty(value = "指标制定部门", position = 10, required = true)
    private String quotaMakeDepartId;

    @ApiModelProperty(value = "指标打分部门", position = 10, required = true)
    private String quotaScoringDepartId;

    @ApiModelProperty(value = "指标积分方法", position = 10, required = true)
    private String quotaIntegrationMethod;

    @ApiModelProperty(value = "指标内容", position = 10, required = true)
    private String quotaContent;

    @ApiModelProperty(value = "多次执行还是一次执行", position = 10, required = true)
    private String onceOrMore;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentQuotaId", referencedColumnName = "quotaId", insertable = false, updatable = false)
    @OrderBy("quotaId asc")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<KpiQuota> kpiQuotas;

}
