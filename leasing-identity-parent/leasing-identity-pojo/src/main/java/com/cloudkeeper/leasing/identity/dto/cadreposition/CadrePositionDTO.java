package com.cloudkeeper.leasing.identity.dto.cadreposition;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
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
 * 岗位管理 DTO
 * @author cqh
 */
@ApiModel(value = "岗位管理 DTO", description = "岗位管理 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CadrePositionDTO extends BaseEditDTO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

    /** 所属组织 */
    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    private String districtId;

    /** 工作地址 */
    @ApiModelProperty(value = "工作地址", position = 10, required = true)
    private String workPlace;

    /** 职责 */
    @ApiModelProperty(value = "职责", position = 10, required = true)
    private String duty;

    /** 在职人员 */
    @ApiModelProperty(value = "在职人员", position = 10, required = true)
    private String cadreId;

}