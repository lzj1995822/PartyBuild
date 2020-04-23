package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.KPIVillageQuotaVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * 村考核指标
 * @author yujian
 */
@ApiModel(value = "村考核指标", description = "村考核指标")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.KPI_village_Quota")
public class KPIVillageQuota extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "镇指标id", position = 10, required = true)
    @Column(length = 60)
    private String townQuotaId;
    @ApiModelProperty(value = "权重", position = 10, required = true)
    @Column(length = 60)
    private String weight;
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String districtId;
    @ApiModelProperty(value = "组织名", position = 10, required = true)
    @Column(length = 60)
    private String districtName;
    @ApiModelProperty(value = "组织名", position = 10, required = true)
    @Column(length = 60)
    private String parentDistrictId;
    @ApiModelProperty(value = "组织名", position = 10, required = true)
    @Column(length = 60)
    private String parentQuotaId;
    @ApiModelProperty(value = "分数", position = 10, required = true)
    @Column(length = 60)
    private String score;
    @ApiModelProperty(value = "最终得分", position = 10, required = true)
    @Column(length = 60)
    private String scoreEnd;
    @ApiModelProperty(value = "计算得分", position = 10, required = true)
    @Column(length = 60)
    private String formulaScore;
    @ApiModelProperty(value = "季度", position = 10, required = true)
    @Column(length = 60)
    private String quarter;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    @JoinColumn(name = "townQuotaId", updatable = false, insertable = false)
    @JsonIgnore
    private KPITownQuota kpiTownQuota;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        KPIVillageQuotaVO kpiVillageQuotaVO = (KPIVillageQuotaVO) convert;
        return (T) kpiVillageQuotaVO;
    }
}
