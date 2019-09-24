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

@ApiModel(value = "云图通知公告和当月活动 VO", description = "云图通知公告和当月活动 VO")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrentActivityVo{

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 10, required = true)
    private String title;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10, required = true)
    private String creatTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }
}
