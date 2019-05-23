package com.cloudkeeper.leasing.identity.dto.paractivityreleasefile;

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
 * 发布任务上传文件 查询DTO
 * @author lxw
 */
@ApiModel(value = "发布任务上传文件 查询DTO", description = "发布任务上传文件 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityReleaseFileSearchable extends BaseSearchable {

    /** 活动Id */
    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    private String activityID;

    /** 文件地址 */
    @ApiModelProperty(value = "文件地址", position = 10, required = true)
    private String Url;

}