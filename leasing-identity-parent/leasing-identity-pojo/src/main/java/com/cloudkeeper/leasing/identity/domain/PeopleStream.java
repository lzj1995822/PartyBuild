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

/**
 * 阵地人流量
 * @author asher
 */
@ApiModel(value = "阵地人流量", description = "阵地人流量")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo.People_Stream")
public class PeopleStream extends BaseEntity {

    /** 地址编码 */
    @ApiModelProperty(value = "地址编码", position = 10, required = true)
    private String locationCode;

    /** 数量 */
    @ApiModelProperty(value = "数量", position = 10, required = true)
    private Integer amount;

}