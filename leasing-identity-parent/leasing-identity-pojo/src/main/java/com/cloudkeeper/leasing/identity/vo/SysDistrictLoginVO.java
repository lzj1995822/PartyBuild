package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysDistrictLoginVO extends BaseVO {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 10, required = true)
    private String districtId;

    /** 组织名字 */
    @ApiModelProperty(value = "组织名字", position = 10, required = true)
    private String districtName;

    /** attachTo */
    @ApiModelProperty(value = "审核组织id", position = 10, required = true)
    private String attachTo;

    @ApiModelProperty(value = "上级组织id", position = 10, required = true)
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

    /** 审核组织名称 */
    @ApiModelProperty(value = "审核组织名称", position = 26, required = true)
    private String parentName;

    /** 上级组织 */
    @ApiModelProperty(value = "上级组织名称", position = 26, required = true)
    private String orgParentName;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String districtType;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 10, required = true)
    private String location;

    @ApiModelProperty(value = "是否属于机关党支部", position = 10, required = true)
    private String isOfficeBranch;

}
