package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 机关活动进度记录
 * @author asher
 */
@ApiModel(value = "机关活动进度记录", description = "机关活动进度记录")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.Activity_Office_Progress")
public class ActivityOfficeProgress extends BaseEntity {

    /** 活动id */
    @ApiModelProperty(value = "活动id", position = 10, required = true)
    @Column(length = 255)
    private String activityId;

    @ApiModelProperty(value = "活动", position = 10, required = true)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "activityId", insertable = false, updatable = false)
    private ParActivity parActivity;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 13)
    @Column(length = 60)
    private String districtId;

    /** 进度 */
    @ApiModelProperty(value = "进度", position = 17)
    @Column(name = "\"percent\"")
    private BigDecimal percent;

}