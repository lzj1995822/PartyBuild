package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.KPIVillageQuotaVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        KPIVillageQuotaVO kpiVillageQuotaVO = (KPIVillageQuotaVO) convert;
        return (T) kpiVillageQuotaVO;
    }
}
