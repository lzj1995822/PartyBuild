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
import java.time.LocalDateTime;

/**
 * 考核积分
 * @author lxw
 */
@ApiModel(value = "考核积分", description = "考核积分")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.EXA_Score")
public class ExaScore extends BaseEntity {

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    @Column(length = 60)
    private String organizationId;

    /** 名称 */
    @ApiModelProperty(value = "活动ID", position = 10, required = true)
    @Column(length = 60)
    private String activityId;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 10, required = true)
    @Column(length = 60)
    private Integer score;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    @Column(length = 60)
    private String type;

    /** 名称 */
    @ApiModelProperty(value = "活动时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate activityTime;

    /** 名称 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime createTime;

}
