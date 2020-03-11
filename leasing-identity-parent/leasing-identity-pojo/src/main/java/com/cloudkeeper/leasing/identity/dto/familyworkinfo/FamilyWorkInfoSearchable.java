package com.cloudkeeper.leasing.identity.dto.familyworkinfo;

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
 * 专职村书记家庭工作情况 查询DTO
 * @author yujian
 */
@ApiModel(value = "专职村书记家庭工作情况 查询DTO", description = "专职村书记家庭工作情况 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FamilyWorkInfoSearchable extends BaseSearchable {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

    /** 称谓 */
    @ApiModelProperty(value = "称谓", position = 10, required = true)
    private String appellation;

    /** 政治面貌 */
    @ApiModelProperty(value = "政治面貌", position = 10, required = true)
    private String politicalStatus;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 10, required = true)
    private String job;

    /** 在村工作时长 */
    @ApiModelProperty(value = "在村工作时长", position = 10, required = true)
    private String duration;

    /** 村主任ID */
    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    private String cadresId;

}