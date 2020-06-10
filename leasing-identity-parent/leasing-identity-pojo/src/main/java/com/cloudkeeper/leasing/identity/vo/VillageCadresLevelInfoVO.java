package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VillageCadresLevelInfoVO implements Serializable {

    @ApiModelProperty(value = "名字", position = 10, required = true)
    private String name;

    @ApiModelProperty(value = "性别", position = 19)
    private String sex;

    @ApiModelProperty(value = "出生日期", position = 19)
    private LocalDate birth;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 10, required = true)
    private String nation;

    /** 身份证 */
    @ApiModelProperty(value = "身份证", position = 10, required = true)
    private String identityCard;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10, required = true)
    private String contact;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 10, required = true)
    private String education;

    @ApiModelProperty(value = "素能评价", position = 19)
    private String evaluation;

    @ApiModelProperty(value = "担任村书记时长", position = 19)
    private String onDutyTime;

    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    private String districtId;

    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    private String districtName;

    /** 所属镇组织 */
    @ApiModelProperty(value = "所属镇组织", position = 10, required = true)
    private String parentDistrictId;

    private List<HonourInfoVO> honours;

    private List<KPIVillageStatisticsVO> kpiVillageStatisticsVOS;
}
