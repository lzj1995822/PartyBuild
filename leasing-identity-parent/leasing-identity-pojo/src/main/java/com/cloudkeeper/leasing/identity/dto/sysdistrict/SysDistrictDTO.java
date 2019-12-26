package com.cloudkeeper.leasing.identity.dto.sysdistrict;

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
 * 组织 DTO
 * @author lxw
 */
@ApiModel(value = "组织 DTO", description = "组织 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysDistrictDTO extends BaseEditDTO {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 10, required = true)
    private String districtId;

    /** 组织名字 */
    @ApiModelProperty(value = "组织名字", position = 10, required = true)
    private String districtName;

    /** 审核组织 */
    @ApiModelProperty(value = "attachTo", position = 10, required = true)
    private String attachTo;

    /** 上级组织 */
    @ApiModelProperty(value = "orgParent", position = 10, required = true)
    private String orgParent;

    /** 组织等级 */
    @ApiModelProperty(value = "组织等级", position = 10, required = true)
    private Integer districtLevel;

    /** subDistrictNum */
    @ApiModelProperty(value = "subDistrictNum", position = 10, required = true)
    private String subDistrictNum;

    /** description */
    @ApiModelProperty(value = "description", position = 10, required = true)
    private String description;

    /** score */
    @ApiModelProperty(value = "score", position = 10, required = true)
    private Integer score;

    /** enable */
    @ApiModelProperty(value = "enable", position = 10, required = true)
    private Integer enable;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String districtType;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 10, required = true)
    private String location;

    @ApiModelProperty(value = "是否属于机关党支部", position = 10, required = true)
    private String isOfficeBranch;

    @ApiModelProperty(value = "是否属于离退休党支部", position = 10, required = true)
    private String isRetiredBranch;

    @ApiModelProperty(value = "任务对象编码", position = 10, required = true)
    private String objectTypeCode;

}
