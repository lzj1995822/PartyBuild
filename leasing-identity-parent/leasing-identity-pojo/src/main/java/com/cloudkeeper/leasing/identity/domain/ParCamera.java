package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.ParCameraVO;
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
 * 监控信息
 * @author cqh
 */
@ApiModel(value = "监控信息", description = "监控信息")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAR_Picture")
public class ParCamera extends BaseEntity {

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 10, required = true)
    @Column(length = 60)
    private String organizationId;

    /** 部门名称*/
    @ApiModelProperty(value = "部门名称", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** IP通道地址*/
    @ApiModelProperty(value = "IP通道地址", position = 10, required = true)
    @Column(length = 60)
    private String IP;

    /** 机顶盒序列号*/
    @ApiModelProperty(value = "机顶盒序列号", position = 10, required = true)
    @Column(length = 60)
    private String number;

    /** 状态*/
    @ApiModelProperty(value = "状态", position = 10, required = true)
    @Column(length = 60)
    private String status;

    /** 时间*/
    @ApiModelProperty(value = "时间", position = 10, required = true)
    @Column(length = 60)
    private String time;

    /** 备注*/
    @ApiModelProperty(value = "备注", position = 10, required = true)
    @Column(length = 60)
    private String remark;

    /** 部门*/
    @ApiModelProperty(value = "部门", position = 24)
    @ManyToOne
    @JoinColumn(name = "organizationId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict sysDistrict;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        ParCameraVO parCameraVO = (ParCameraVO) convert;
        if(!StringUtils.isEmpty(this.sysDistrict)){
            parCameraVO.setName(this.sysDistrict.getDistrictName());
            parCameraVO.setDistrictId(this.sysDistrict.getDistrictId());
        }
        return (T) parCameraVO;
    }
}
