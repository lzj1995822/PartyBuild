package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VillageCadresInfoVO implements Serializable {

    @ApiModelProperty(value = "名字", position = 10, required = true)
    private String name;

    @ApiModelProperty(value = "状态", position = 19)
    private String state;

    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    private String districtId;

    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    private String districtName;

    /** 所属镇组织 */
    @ApiModelProperty(value = "所属镇组织", position = 10, required = true)
    private String parentDistrictId;
}
