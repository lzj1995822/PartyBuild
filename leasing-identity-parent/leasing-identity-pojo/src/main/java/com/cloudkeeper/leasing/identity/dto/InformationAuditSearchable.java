package com.cloudkeeper.leasing.identity.dto;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * 消息通知 查询DTO
 *
 * @author zdw
 */
@ApiModel(value = "村干部审核 查询DTO", description = "村干部审核 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class InformationAuditSearchable extends BaseSearchable {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", position = 10, required = true)
    private String description;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间", position = 10, required = true)
    private LocalDateTime releaseTime;

    /**
     * 发布对象
     */
    @ApiModelProperty(value = "发布对象", position = 10, required = true)
    private String districtID;


    /**村书记id**/
    private String villageId;

    /**流程类型（村书记基本信息，考核，职级评定）**/
    private String processType;

    /**村书记任务Id**/
    private String taskId;
}
