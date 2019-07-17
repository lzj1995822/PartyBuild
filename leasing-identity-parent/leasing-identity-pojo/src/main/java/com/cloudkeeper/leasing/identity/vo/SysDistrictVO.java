package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import com.cloudkeeper.leasing.identity.domain.PositionInformation;
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
import java.util.List;

/**
 * 组织 VO
 * @author lxw
 */
@ApiModel(value = "组织 VO", description = "组织 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysDistrictVO extends BaseVO {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 10, required = true)
    private String districtId;

    /** 组织名字 */
    @ApiModelProperty(value = "组织名字", position = 10, required = true)
    private String districtName;

    /** attachTo */
    @ApiModelProperty(value = "attachTo", position = 10, required = true)
    private String attachTo;

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

    /** 上级组织名称 */
    @ApiModelProperty(value = "上级组织名称", position = 26, required = true)
    private String parentName;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String districtType;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 10, required = true)
    private String location;

    /** 阵地信息 */
    @ApiModelProperty(value = "阵地信息", position = 10, required = true)
    private List<PositionInformation> positionInformation;


}
