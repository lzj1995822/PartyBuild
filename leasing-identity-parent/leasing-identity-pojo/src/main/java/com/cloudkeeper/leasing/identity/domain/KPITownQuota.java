package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.KPITownQuotaVO;
import com.cloudkeeper.leasing.identity.vo.KPIVillageQuotaVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.List;

/**
 * 镇考核指标
 * @author yujian
 */
@ApiModel(value = "镇考核指标", description = "镇考核指标")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.KPI_Town_Quota")
public class KPITownQuota extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String districtId;
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    @Column(length = 60)
    private String districtName;
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    @Column(length = 60)
    private String taskId;
    @ApiModelProperty(value = "指标名称", position = 10, required = true)
    @Column(length = 60)
    private String quotaName;
    @ApiModelProperty(value = "父指标标识", position = 10, required = true)
    @Column(length = 60)
    private String parentQuotaId;
    @ApiModelProperty(value = "父指标名称", position = 10, required = true)
    @Column(length = 60)
    private String parentQuotaName;
    @ApiModelProperty(value = "分值", position = 10, required = true)
    @Column(length = 60)
    private String score;
    @ApiModelProperty(value = "季度", position = 10, required = true)
    @Column(length = 60)
    private String quarter;

    @ApiModelProperty(value = "季度", position = 10, required = true)
    @Column(length = 60)
    private String quotaYear;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "townQuotaId", insertable = false, updatable = false)
    @OrderBy("districtId desc")
    @ApiModelProperty(value = "子类", position = 19)
    List<KPIVillageQuota> kpiVillageQuotas;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        KPITownQuotaVO kpiTownQuotaVO = (KPITownQuotaVO) convert;
        if (!StringUtils.isEmpty(this.kpiVillageQuotas)) {
            kpiTownQuotaVO.setKpiVillageQuotas(KPIVillageQuota.convert(this.kpiVillageQuotas, KPIVillageQuotaVO.class));
        }
        return (T) kpiTownQuotaVO;
    }


}
