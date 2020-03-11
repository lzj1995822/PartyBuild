package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 专职村书记家庭情况 VO
 * @author yujian
 */
@ApiModel(value = "专职村书记家庭情况 VO", description = "专职村书记家庭情况 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FamilyInfoVO extends BaseVO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

    /** 称谓 */
    @ApiModelProperty(value = "称谓", position = 10, required = true)
    private String appellation;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 10, required = true)
    private String birthDay;

    /** 政治面貌 */
    @ApiModelProperty(value = "政治面貌", position = 10, required = true)
    private String politicalStatus;

    /** 作单位及职务 */
    @ApiModelProperty(value = "作单位及职务", position = 10, required = true)
    private String orgAndJob;
    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    private String cadresId;
}