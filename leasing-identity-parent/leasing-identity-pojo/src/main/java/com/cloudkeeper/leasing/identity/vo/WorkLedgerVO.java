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
 * 工作台账 VO
 * @author cqh
 */
@ApiModel(value = "工作台账 VO", description = "工作台账 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class WorkLedgerVO extends BaseVO {

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    private String title;

    /** 工作时间 */
    @ApiModelProperty(value = "工作时间", position = 10, required = true)
    private LocalDateTime workTime;

    /** 台账类型 */
    @ApiModelProperty(value = "台账类型", position = 10, required = true)
    private String ledgerType;

    /** 工作地址 */
    @ApiModelProperty(value = "工作地址", position = 10, required = true)
    private String workAddress;

    /** 具体内容 */
    @ApiModelProperty(value = "具体内容", position = 10, required = true)
    private String content;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10, required = true)
    private String enclosure;

}