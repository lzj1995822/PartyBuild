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
}
