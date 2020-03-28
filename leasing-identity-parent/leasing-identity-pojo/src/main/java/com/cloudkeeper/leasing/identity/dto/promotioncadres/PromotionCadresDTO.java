package com.cloudkeeper.leasing.identity.dto.promotioncadres;

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
 * 村书记拟晋升名单 DTO
 * @author asher
 */
@ApiModel(value = "村书记拟晋升名单 DTO", description = "村书记拟晋升名单 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PromotionCadresDTO extends BaseEditDTO {

    private String cadresName;

    private String postName;

    private String purposeLevelName;

    private String villageId;

    private String cadresId;

    private String townId;

    private String taskId;

    // 晋升状态
    private String status;

}