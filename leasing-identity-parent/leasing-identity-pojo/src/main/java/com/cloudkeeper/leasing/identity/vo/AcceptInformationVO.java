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
 * 接收公告 VO
 * @author cqh
 */
@ApiModel(value = "接收公告 VO", description = "接收公告 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AcceptInformationVO extends BaseVO {

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    private String title;

    /** 发布人 */
    @ApiModelProperty(value = "发布人", position = 10, required = true)
    private String authorId;

    /** 接收组织 */
    @ApiModelProperty(value = "接收组织", position = 10, required = true)
    private String objs;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    private LocalDateTime creatTime;

    /** 是否接收 */
    @ApiModelProperty(value = "是否接收", position = 10, required = true)
    private String status;

    /** 公告Id */
    @ApiModelProperty(value = "公告Id", position = 10, required = true)
    private String informationId;

    /** 发布组织 */
    @ApiModelProperty(value = "发布组织", position = 10, required = true)
    private String name;

    /** 公告内容 */
    @ApiModelProperty(value = "公告内容", position = 10, required = true)
    private String description;

}