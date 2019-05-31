package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.SysDistrictVO;
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
 * 组织
 * @author lxw
 */
@ApiModel(value = "组织", description = "组织")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.SYS_District")
public class SysDistrict extends BaseEntity {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 组织名字 */
    @ApiModelProperty(value = "组织名字", position = 10, required = true)
    @Column(length = 60)
    private String districtName;

    /** 上级id */
    @ApiModelProperty(value = "attachTo", position = 10, required = true)
    @Column(length = 60)
    private String attachTo;

    /** 组织等级 */
    @ApiModelProperty(value = "组织等级", position = 10, required = true)
    @Column(length = 60)
    private Integer districtLevel;


    /** 下属组织 */
    @ApiModelProperty(value = "subDistrictNum", position = 10, required = true)
    @Column(length = 60)
    private String subDistrictNum;

    /** 描述 */
    @ApiModelProperty(value = "description", position = 10, required = true)
    @Column(length = 60)
    private String description;

    /** 分数 */
    @ApiModelProperty(value = "score", position = 10, required = true)
    @Column(length = 60)
    private Integer score;

    /** 是否可用 */
    @ApiModelProperty(value = "enable", position = 10, required = true)
    @Column(length = 60)
    private Integer enable;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 24)
    @ManyToOne
    @JoinColumn(name = "attachTo",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict sysDistrict;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        SysDistrictVO sysDistrictVO = (SysDistrictVO) convert;
        if(!StringUtils.isEmpty(this.sysDistrict)){
            sysDistrictVO.setParentName(this.sysDistrict.getDistrictName());
        }
        return (T) sysDistrictVO;
    }


}