package com.cloudkeeper.leasing.identity.dto.paractivity;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
import com.cloudkeeper.leasing.identity.domain.DistLearningActivityVideo;
import com.cloudkeeper.leasing.identity.domain.TaskObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动 DTO
 * @author lxw
 */
@ApiModel(value = "活动 DTO", description = "活动 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityDTO extends BaseEditDTO {

    /** 截止日期 */
    @ApiModelProperty(value = "截止日期", position = 10, required = true)
    private LocalDate month;

    /** 背景 */
    @ApiModelProperty(value = "背景", position = 12)
    private String context;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 14)
    private String title;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 16)
    private String status;

    /** 区ID */
    @ApiModelProperty(value = "区ID", position = 18)
    private String districtID;

    /** 模板id */
    @ApiModelProperty(value = "模板id", position = 19)
    private String templateId;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 20)
    private String type;

    /** 任务类型 */
    @ApiModelProperty(value = "任务类型", position = 22)
    private String taskType;

    /** 释放时间 */
    @ApiModelProperty(value = "释放时间", position = 24)
    private LocalDateTime releaseTime;

    /** 报警时间 */
    @ApiModelProperty(value = "报警时间", position = 26)
    private LocalDateTime alarmTime;

    /** 分数 */
    @ApiModelProperty(value = "分数", position = 28)
    private Integer score;

    /** 文件 */
    @ApiModelProperty(value = "文件", position = 30)
    private List<String> fileUrls;

    /** 需要执行任务的对象id集合 */
    @ApiModelProperty(value = "需要执行任务的对象id集合", position = 32)
    private List<String> objIds;

    /** 视频 */
    @ApiModelProperty(value = "视频", position = 34)
    private List<DistLearningActivityVideo> video;

    /** 任务对象 */
    @ApiModelProperty(value = "任务对象", position = 36)
    private TaskObject taskObject;

    /**
     * 农村 objectType 1
     * 市直机关工委以及所有党组织加各局党委以及所属所有党组织 objectType 2-1
     * 市直机关工委以及所有所属党组织 objectType 2-2
     * 市直机关工委以及所有所属党组织加上各局委所属机关党支部 objectType 2-3
     */
    @ApiModelProperty(value="任务对象类型",position = 10,required = true)
    private String objectType;

    @ApiModelProperty(value="所需上传的材料的名字拼接",position = 10)
    private String templateItem;
}
