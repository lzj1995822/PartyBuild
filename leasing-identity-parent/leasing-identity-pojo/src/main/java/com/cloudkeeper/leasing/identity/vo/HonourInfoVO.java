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
 * 表彰情况 VO
 * @author asher
 */
@ApiModel(value = "表彰情况 VO", description = "表彰情况 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class HonourInfoVO extends BaseVO {

    /** 干部id */
    @ApiModelProperty(value = "干部id", position = 5)
    private String cadresId;
    @ApiModelProperty(value = "干部名称", position = 5)
    private String cadresName;
    /** 获取时间 */
    @ApiModelProperty(value = "获取时间", position = 1)
    private LocalDate achieveTime;

    /** 表彰内容 */
    @ApiModelProperty(value = "表彰内容", position = 10)
    private String content;

    /** 表彰描述（字段备用） */
    @ApiModelProperty(value = "表彰描述（字段备用）", position = 15)
    private String des;

    /** 佐证材料 */
    @ApiModelProperty(value = "佐证材料", position = 20)
    private String supportDoc;

    /** 是否可以编辑 */
    @ApiModelProperty(value = "是否可以编辑", position = 25)
    private String isEdit;

    /** 2020-03-12新增字段 **/
    /** 奖或惩 */
    @ApiModelProperty(value = "奖或惩", position = 25)
    private String rewardsType;

    /** 表彰种类 */
    @ApiModelProperty(value = "表彰种类", position = 25)
    private String honourDescription;
    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 25)
    private String districtId;

}
