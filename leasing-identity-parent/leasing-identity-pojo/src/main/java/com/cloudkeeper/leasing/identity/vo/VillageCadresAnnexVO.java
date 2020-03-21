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
 * 村主任信息附件 VO
 * @author yujian
 */
@ApiModel(value = "村主任信息附件 VO", description = "村主任信息附件 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VillageCadresAnnexVO extends BaseVO {

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 10, required = true)
    private String type;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径", position = 10, required = true)
    private String filePath;

    /** 村主任ID */
    @ApiModelProperty(value = "村主任ID", position = 10, required = true)
    private String cadresId;

}