package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 发布任务上传文件 VO
 * @author lxw
 */
@ApiModel(value = "发布任务上传文件 VO", description = "发布任务上传文件 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParActivityReleaseFileVO extends BaseVO {

    /** 活动Id */
    @ApiModelProperty(value = "活动Id", position = 10, required = true)
    private String activityID;

    /** 文件地址 */
    @ApiModelProperty(value = "文件地址", position = 10, required = true)
    private String Url;

}
