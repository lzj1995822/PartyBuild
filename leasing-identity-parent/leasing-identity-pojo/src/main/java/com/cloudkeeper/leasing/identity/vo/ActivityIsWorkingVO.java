package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityIsWorkingVO extends BaseVO {

    @ApiModelProperty(value = "活动id", position = 10, required = true)
    private String objectId;

    @ApiModelProperty(value = "最新图片时间", position = 10, required = true)
    private LocalDateTime newTime;
}
