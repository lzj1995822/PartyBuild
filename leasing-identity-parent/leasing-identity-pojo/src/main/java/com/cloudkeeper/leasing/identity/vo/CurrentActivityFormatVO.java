package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@ApiModel(value = "云图通知公告和当月活动轮播图格式 VO", description = "云图通知公告和当月活动轮播图格式 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrentActivityFormatVO {

    /** 标题 */
    @ApiModelProperty(value = "标题名称", position = 10, required = true)
    private String name;

    /** 创建时间 */
    @ApiModelProperty(value = "对其方式", position = 10, required = true)
    private String textAlign;

    /** 绑定的字段 */
    @ApiModelProperty(value = "绑定的字段", position = 10, required = true)
    private String id;

    /** 单位 */
    @ApiModelProperty(value = "单位", position = 10, required = true)
    private String unit;

    /** 自动转行 */
    @ApiModelProperty(value = "自动转行", position = 10, required = true)
    private Boolean autoWrap;

    /** 列宽度 */
    @ApiModelProperty(value = "列宽度", position = 10, required = true)
    private String width;

    /** 数据是小数时，保留的位数（四舍五入，不够位数会补零 */
    @ApiModelProperty(value = "数据是小数时，保留的位数（四舍五入，不够位数会补零）", position = 10, required = true)
    private String accuracy;

    /** 字段说明 */
    @ApiModelProperty(value = "字段说明", position = 10, required = true)
    private String remark;

}
