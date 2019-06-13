package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
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
import java.util.List;

/**
 * 任务执行记录
 * @author lxw
 */
@ApiModel(value = "任务执行记录", description = "任务执行记录")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_ActivityPerform")
public class ParActivityPerform extends BaseEntity {

    /** 活动ID */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    @Column(name="ActivityID",length = 60)
    private String activityID;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String organizationId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10, required = true)
    @Column(length = 60)
    private String status;

    /** 数据源 */
    @ApiModelProperty(value = "数据源", position = 10, required = true)
    @Column(length = 60)
    private Integer source;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 10, required = true)
    @OneToOne
    @JoinColumn(name = "organizationId", insertable = false, updatable = false)
    private SysDistrict sysDistrict;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        ParActivityPerformVO parActivityPerformVO = (ParActivityPerformVO) convert;
        if(!StringUtils.isEmpty(this.sysDistrict)){
            parActivityPerformVO.setDistrictName(this.sysDistrict.getDistrictName());
        }
        return (T) parActivityPerformVO;
    }
}
