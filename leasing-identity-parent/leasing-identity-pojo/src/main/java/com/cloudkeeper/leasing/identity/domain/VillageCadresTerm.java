package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.VillageCadresTermVO;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * 村主任任期信息
 * @author yujian
 */
@ApiModel(value = "村主任任期信息", description = "村主任任期信息")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.village_cadres_term")
public class VillageCadresTerm extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "村主任名称", position = 10, required = true)
    @Column(length = 60)
    private String cadresName;

    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    @Column(length = 60)
    private String cadresId;

    @ApiModelProperty(value = "上任时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate appointmentTime;

    @ApiModelProperty(value = "离任时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate departureTime;

    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    @Column(length = 60)
    private String districtName;

    @ApiModelProperty(value = "离退休材料", position = 10, required = true)
    @Column(length = 60)
    private String termFile;

    @ApiModelProperty(value = "人员类型", position = 10, required = true)
    @Column(length = 60)
    private String cadresType;

    @ApiModelProperty(value = "任期类型（新任/离任）0 新任 1离任", position = 10, required = true)
    @Column(length = 60)
    private String termType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private VillageCadres villageCadres;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        VillageCadresTermVO villageCadresTermVO = (VillageCadresTermVO) convert;
        if (villageCadres != null) {
            villageCadresTermVO.setHeadSculpture(villageCadres.getHeadSculpture());
        }
        return (T) villageCadresTermVO;
    }

}
