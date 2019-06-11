package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;

import com.cloudkeeper.leasing.identity.vo.ParActivityObjectVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 * 任务对象
 * @author lxw
 */
@ApiModel(value = "任务对象", description = "任务对象")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_ActivityObject")
public class ParActivityObject extends BaseEntity {

    /** 活动Id */
    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    @Column(length = 60)
    private String activityId;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String organizationId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    @Column(length = 60)
    private String status;

    /** 是否在用 */
    @ApiModelProperty(value = "是否在用", position = 10, required = true)
    @Column(length = 60)
    private String isWorking;

    /** 关联组织 */
    @ApiModelProperty(value = "关联组织", position = 10, required = true)
    @Column(length = 60)
    private String attachTo;

    /** 活动信息 */
    @ApiModelProperty(value = "活动信息", position = 10, required = true)
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "activityId", insertable = false, updatable = false)
    private ParActivity parActivity;

    /** 组织信息 */
    @ApiModelProperty(value = "组织信息", position = 10, required = true)
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "organizationId", referencedColumnName = "districtId", insertable = false, updatable = false)
    private SysDistrict sysDistrict;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        ParActivityObjectVO parActivityObjectVO = (ParActivityObjectVO) convert;
        if(!StringUtils.isEmpty(this.parActivity)){
            parActivityObjectVO.setTitle(this.parActivity.getTitle());
            parActivityObjectVO.setType(this.parActivity.getType());
            parActivityObjectVO.setTaskType(this.parActivity.getTaskType());
            parActivityObjectVO.setMonth(this.parActivity.getMonth());
            parActivityObjectVO.setContext(this.parActivity.getContext());
            parActivityObjectVO.setScore(this.parActivity.getScore());
            parActivityObjectVO.setDistrictId(this.sysDistrict.getId());
        }
        if(!StringUtils.isEmpty(this.sysDistrict)){
            parActivityObjectVO.setDistrictName(this.sysDistrict.getDistrictName());
            parActivityObjectVO.setDistrictId(this.sysDistrict.getId());
        }
        return (T) parActivityObjectVO;
    }

}
