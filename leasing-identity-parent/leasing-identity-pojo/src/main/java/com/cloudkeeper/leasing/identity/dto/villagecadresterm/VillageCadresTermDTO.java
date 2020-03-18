package com.cloudkeeper.leasing.identity.dto.villagecadresterm;

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
 * 村主任任期信息 DTO
 * @author yujian
 */
@ApiModel(value = "村主任任期信息 DTO", description = "村主任任期信息 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VillageCadresTermDTO extends BaseEditDTO {

    /** 村主任名称 */
    @ApiModelProperty(value = "村主任名称", position = 10, required = true)
    private String cadresName;

    /** 村主任ID */
    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    private String cadresId;

    /** 上任时间 */
    @ApiModelProperty(value = "上任时间", position = 10, required = true)
    private LocalDate appointmentTime;

    /** 离任时间 */
    @ApiModelProperty(value = "离任时间", position = 10, required = true)
    private LocalDate departureTime;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String districtId;

}