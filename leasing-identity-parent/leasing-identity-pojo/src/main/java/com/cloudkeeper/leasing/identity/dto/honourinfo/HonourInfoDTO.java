package com.cloudkeeper.leasing.identity.dto.honourinfo;

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
 * 表彰情况 DTO
 * @author asher
 */
@ApiModel(value = "表彰情况 DTO", description = "表彰情况 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class HonourInfoDTO extends BaseEditDTO {

    /** 干部id */
    @ApiModelProperty(value = "干部id", position = 5)
    private String cadresId;

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

}