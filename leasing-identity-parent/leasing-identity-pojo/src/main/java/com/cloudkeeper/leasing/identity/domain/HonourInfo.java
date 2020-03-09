package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 表彰情况
 * @author asher
 */
@ApiModel(value = "表彰情况", description = "表彰情况")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.Honour_Info")
public class HonourInfo extends BaseEntity {

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

    /** 表彰类型 */
    @ApiModelProperty(value = "表彰类型", position = 25)
    private String honourType;

}