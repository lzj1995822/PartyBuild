package com.cloudkeeper.leasing.identity.dto.paractivitypicture;

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
 * 手机截图 查询DTO
 * @author lxw
 */
@ApiModel(value = "手机截图 查询DTO", description = "手机截图 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityPictureSearchable extends BaseSearchable {

    /** feedbackID */
    @ApiModelProperty(value = "feedbackID", position = 10, required = true)
    private String activityID;

    /** 图片地址 */
    @ApiModelProperty(value = "图片地址", position = 10, required = true)
    private String ImageUrl;

}