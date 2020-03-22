package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 村主任任期信息 VO
 * @author yujian
 */
@ApiModel(value = "村主任任期信息 VO", description = "村主任任期信息 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VillageCadresTermVO extends BaseVO {

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
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

    @ApiModelProperty(value = "人员类型", position = 10, required = true)
    private String cadresType;
    @ApiModelProperty(value = "离退休材料", position = 10, required = true)
    private String termFile;

}
