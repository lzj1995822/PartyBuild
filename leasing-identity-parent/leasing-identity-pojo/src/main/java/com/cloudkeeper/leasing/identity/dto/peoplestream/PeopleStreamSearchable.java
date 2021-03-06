package com.cloudkeeper.leasing.identity.dto.peoplestream;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
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
 * 阵地人流量 查询DTO
 * @author asher
 */
@ApiModel(value = "阵地人流量 查询DTO", description = "阵地人流量 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PeopleStreamSearchable extends BaseSearchable {

    /** 地址编码 */
    @ApiModelProperty(value = "地址编码", position = 10, required = true)
    private String locationCode;

    /** 数量 */
    @ApiModelProperty(value = "数量", position = 10, required = true)
    private Integer amount;

}