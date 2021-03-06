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
 * 电视截图 VO
 * @author lxw
 */
@ApiModel(value = "电视截图 VO", description = "电视截图 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParPictureInfroVO extends BaseVO {

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    private LocalDateTime createTime;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 10, required = true)
    private String organizationId;

    /** 图片地址 */
    @ApiModelProperty(value = "图片地址", position = 10, required = true)
    private String imageURL;

    /** 任务ID */
    @ApiModelProperty(value = "任务ID", position = 10, required = true)
    private String studyContent;

}
