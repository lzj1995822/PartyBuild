package com.cloudkeeper.leasing.identity.dto.camera;

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
 * 监控 查询DTO
 * @author asher
 */
@ApiModel(value = "监控 查询DTO", description = "监控 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CameraSearchable extends BaseSearchable {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 10, required = true)
    private String pid;

    private String cameraUuid;

    private String regionUuid;

    private String cameraName;

    private String nickName;

    private int cameraType;

    private String keyBoardCode;

    private String rtspURL;

    private int refreshCheck;

    private int enable;

}