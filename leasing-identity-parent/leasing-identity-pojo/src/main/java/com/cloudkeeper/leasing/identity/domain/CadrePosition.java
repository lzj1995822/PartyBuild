package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.CadrePositionVO;
import com.cloudkeeper.leasing.identity.vo.SysDistrictVO;
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
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * 岗位管理
 * @author cqh
 */
@ApiModel(value = "岗位管理", description = "岗位管理")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cadre_position")
public class CadrePosition extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 所属组织 */
    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 所属镇组织 */
    @ApiModelProperty(value = "所属镇组织", position = 10, required = true)
    @Column(length = 60)
    private String parentDistrictId;

    /** 镇组织 */
    @ApiModelProperty(value = "镇组织", position = 24)
    @ManyToOne
    @JoinColumn(name = "parentDistrictId",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict sysParentDistrict;


    /** 组织 */
    @ApiModelProperty(value = "组织", position = 24)
    @ManyToOne
    @JoinColumn(name = "districtId",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict sysDistrict;

    /** 工作地址 */
    @ApiModelProperty(value = "工作地址", position = 10, required = true)
    @Column(length = 60)
    private String workPlace;

    /** 职责 */
    @ApiModelProperty(value = "职责", position = 10, required = true)
    @Column(length = 60)
    private String duty;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 10, required = true)
    @Column(length = 60)
    private String post;

    /** 在职人员 */
    @ApiModelProperty(value = "在职人员", position = 10, required = true)
    @Column(length = 60)
    private String cadreId;

    /** 人员 */
    @ApiModelProperty(value = "人员", position = 24)
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "cadreId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private VillageCadres villageCadres;


    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        CadrePositionVO cadrePositionVO = (CadrePositionVO) convert;
        if(!StringUtils.isEmpty(this.villageCadres)){
            cadrePositionVO.setCadreName(this.villageCadres.getName());
        }
        if(!StringUtils.isEmpty((this.sysDistrict))){
            cadrePositionVO.setDistrictName(this.sysDistrict.getDistrictName());
        }
        if (!StringUtils.isEmpty(this.sysParentDistrict)) {
            cadrePositionVO.setParentDistrictName(this.sysParentDistrict.getDistrictName());
        }
        return (T) cadrePositionVO;
    }
}