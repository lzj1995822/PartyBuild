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
 * 手机截图 VO
 * @author lxw
 */
@ApiModel(value = "手机截图 VO", description = "手机截图 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityPictureVO extends BaseVO {

    /** feedbackID */
    @ApiModelProperty(value = "feedbackID", position = 10, required = true)
    private String activityID;

    /** 图片地址 */
    @ApiModelProperty(value = "图片地址", position = 10, required = true)
    private String ImageUrl;

}