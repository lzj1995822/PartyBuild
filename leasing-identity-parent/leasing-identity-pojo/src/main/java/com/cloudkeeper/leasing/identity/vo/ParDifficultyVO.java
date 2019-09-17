package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
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
 * 困难党员 VO
 * @author cqh
 */
@ApiModel(value = "困难党员 VO", description = "困难党员 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParDifficultyVO extends BaseVO {

    /** 党员 */
    @ApiModelProperty(value = "党员", position = 10, required = true)
    private String partyMemberId;

    /** 当前是否困难 */
    @ApiModelProperty(value = "当前是否困难", position = 10, required = true)
    private String isDifficulty;

    /** 组织认定 */
    @ApiModelProperty(value = "组织认定", position = 10, required = true)
    private String organizationalIdentity;

    /** 收入来源 */
    @ApiModelProperty(value = "收入来源", position = 10, required = true)
    private String incomeSource;

    /** 年均收入 */
    @ApiModelProperty(value = "年均收入", position = 10, required = true)
    private String averageIncome;

    /** 贫困原因 */
    @ApiModelProperty(value = "贫困原因", position = 10, required = true)
    private String povertyCauses;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10, required = true)
    private String memberName;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 10, required = true)
    private String districtId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

}