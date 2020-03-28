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
 * 村书记拟晋升名单 VO
 * @author asher
 */
@ApiModel(value = "村书记拟晋升名单 VO", description = "村书记拟晋升名单 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PromotionCadresVO extends BaseVO {

    private String cadresName;

    private String postName;

    private String purposeLevelName;

    private String villageId;

    private String cadresId;

    private String townId;

    private String taskId;

    // 是否破格
    private String isBreakRule;

    // 理由
    private String reason;

    // 晋升状态
    private String status;

}