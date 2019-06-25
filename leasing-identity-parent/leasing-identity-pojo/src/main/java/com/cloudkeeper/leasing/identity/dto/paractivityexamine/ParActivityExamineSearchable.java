package com.cloudkeeper.leasing.identity.dto.paractivityexamine;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 活动考核记录 查询DTO
 * @author lxw
 */
@ApiModel(value = "活动考核记录 查询DTO", description = "活动考核记录 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityExamineSearchable extends BaseSearchable {

    /** 记录PerformID */
    @ApiModelProperty(value = "记录PerformID", position = 10, required = true)
    private String pId;

    /** 审核说明 */
    @ApiModelProperty(value = "审核说明", position = 10, required = true)
    private String remark;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    private LocalDate createTime;

}
