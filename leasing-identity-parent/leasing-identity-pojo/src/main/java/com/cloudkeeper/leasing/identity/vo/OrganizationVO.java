package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.identity.enumeration.OrganizationTypeEnum;
import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织vo
 * @author jerry
 */
@ApiModel(value = "组织vo", description = "组织vo")
@Getter
@Setter
public class OrganizationVO extends BaseVO {

    /** 编码 */
    @ApiModelProperty(value = "编码", position = 10, required = true)
    @Column(length = 24)
    private String districtId;

    /** 全编码 */
    @ApiModelProperty(value = "全编码", position = 11, required = true)
    @Column(length = 250)
    private String attachTo;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 12, required = true)
    @Column(length = 50)
    private String districtName;

    /** 等级 */
    @ApiModelProperty(value = "等级", position = 14)
    @Column(length = 30)
    private String districtLevel;

    /** 等级 */
    @ApiModelProperty(value = "等级", position = 14)
    @Column(length = 30)
    private String subDistrictNum;


    /** 排序 */
    @ApiModelProperty(value = "排序", position = 18, required = true)
    private Integer score;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 20)
    @Column(length = 1000)
    private String description;

    /** 是否可用 */
    @ApiModelProperty(value = "是否可用", position = 20)
    private Integer enable;

    /** 子节点*/
    @ApiModelProperty(value = "子节点", position = 22)
    private List<OrganizationVO> children = new ArrayList<>();

    /** 角色*/
    @ApiModelProperty(value = "角色", position = 24)
    private List<RoleVO> roleVOList;
}
