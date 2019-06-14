package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.PositionInformationVO;
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
 * 阵地信息
 * @author cqh
 */
@ApiModel(value = "阵地信息", description = "阵地信息")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Position_Information")
public class PositionInformation extends BaseEntity {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    @Column(length = 60)
    private String type;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 设施 */
    @ApiModelProperty(value = "设施", position = 10, required = true)
    @Column(length = 60)
    private String facilities;

    /** 面积 */
    @ApiModelProperty(value = "面积", position = 10, required = true)
    @Column(length = 60)
    private String area;

    /** 功能介绍 */
    @ApiModelProperty(value = "功能介绍", position = 10, required = true)
    @Column(length = 60)
    private String introduction;

    /** 图片 */
    @ApiModelProperty(value = "图片", position = 10, required = true)
    @Column(length = 60)
    private String pictures;

    /** 热度 */
    @ApiModelProperty(value = "热度", position = 10, required = true)
    @Column(length = 60)
    private Integer hotDegree;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 24)
    @ManyToOne
    @JoinColumn(name = "districtId" ,referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    private SysDistrict sysDistrict;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        PositionInformationVO positionInformationVO = (PositionInformationVO) convert;
        if(!StringUtils.isEmpty(this.sysDistrict)){
            positionInformationVO.setDistrictName(this.sysDistrict.getDistrictName());
        }
        return (T) positionInformationVO;
    }


}