package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
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
 * 志愿者 VO
 * @author asher
 */
@ApiModel(value = "志愿者 VO", description = "志愿者 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerVO extends BaseVO {

    /** 党员表id */
    @ApiModelProperty(value = "党员表id", position = 10, required = true)
    private String partyMemberId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10, required = true)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 10)
    private String sex;

    /** 出生年月 */
    @ApiModelProperty(value = "出生年月", position = 10)
    private LocalDate birthday;

    /** 入党时间 */
    @ApiModelProperty(value = "入党时间", position = 10)
    private LocalDate joinDate;

    /** 服务目录 */
    @ApiModelProperty(value = "服务目录", position = 10)
    private String category;

    /** 志愿者承诺 */
    @ApiModelProperty(value = "志愿者承诺", position = 10)
    private String promise;

    /** 其他服务目录 */
    @ApiModelProperty(value = "其他服务目录", position = 10)
    private String otherCategory;

    /** 所属组织 */
    @ApiModelProperty(value = "所属组织", position = 33)
    private String districtId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 33)
    private String districtName;


}