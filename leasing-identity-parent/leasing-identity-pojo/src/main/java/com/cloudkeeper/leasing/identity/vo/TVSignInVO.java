package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 远教视频签到记录 VO
 * @author cqh
 */
@ApiModel(value = "远教视频签到记录 VO", description = "远教视频签到记录 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TVSignInVO extends BaseVO {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String activityId;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    private String organizationId;

    /** 视频Id */
    @ApiModelProperty(value = "视频Id", position = 10, required = true)
    private String videoId;

    /** 签到 */
    @ApiModelProperty(value = "签到", position = 10, required = true)
    private String signInRecord;

    /** 签退 */
    @ApiModelProperty(value = "签退", position = 10, required = true)
    private String signOutRecord;

    /** 是否已读 */
    @ApiModelProperty(value = "是否已读", position = 10, required = true)
    private Integer flag;

    /** 视频名称 */
    @ApiModelProperty(value = "视频名称", position = 10, required = true)
    private String videoName;

    /** 视频时长 */
    @ApiModelProperty(value = "视频时长", position = 10, required = true)
    private String lengthOfTime;

    /** 视频封面 */
    @ApiModelProperty(value = "视频封面", position = 10, required = true)
    private String videoCover;

    /** 视频地址 */
    @ApiModelProperty(value = "视频地址", position = 10, required = true)
    private String videoUrl;

}