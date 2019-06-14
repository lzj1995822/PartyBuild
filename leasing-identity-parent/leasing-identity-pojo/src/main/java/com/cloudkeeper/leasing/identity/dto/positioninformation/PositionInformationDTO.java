package com.cloudkeeper.leasing.identity.dto.positioninformation;

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
 * 阵地信息 DTO
 * @author cqh
 */
@ApiModel(value = "阵地信息 DTO", description = "阵地信息 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PositionInformationDTO extends BaseEditDTO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String name;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 10, required = true)
    private String districtId;

    /** 设施 */
    @ApiModelProperty(value = "设施", position = 10, required = true)
    private String facilities;

    /** 面积 */
    @ApiModelProperty(value = "面积", position = 10, required = true)
    private String area;

    /** 功能介绍 */
    @ApiModelProperty(value = "功能介绍", position = 10, required = true)
    private String introduction;

    /** 图片 */
    @ApiModelProperty(value = "图片", position = 10, required = true)
    private String pictures;

    /** 热度 */
    @ApiModelProperty(value = "热度", position = 10, required = true)
    private Integer hotDegree;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 10, required = true)
    private String lonLat;
}