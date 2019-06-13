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
import java.time.LocalDateTime;

/**
 * 活动考核记录
 * @author lxw
 */
@ApiModel(value = "活动考核记录", description = "活动考核记录")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.PAR_ActivityExamine")
public class ParActivityExamine extends BaseEntity {

    /** 记录PerformID */
    @ApiModelProperty(value = "记录PerformID", position = 10, required = true)
    @Column(length = 60)
    private String pId;

    /** 审核说明 */
    @ApiModelProperty(value = "审核说明", position = 10, required = true)
    @Column(length = 60)
    private String remark;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDateTime createTime;

}
