package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * easyNVR controller
 * @author asher
 */
@Api(value = "easyNVR", tags = "easyNVR")
@RequestMapping("/easyNVR")
public interface EasyNVRController {

    /**
     * 截图
     */
    @ApiOperation(value = "截图", notes = "截图", position = 1)
    @Authorization(required = true)
    @GetMapping("/number")
    Result<String> findOne(@ApiParam(value = "机顶盒编号", required = true) String number,
                            @ApiParam(value = "活动Id", required = true)  String activityId,
                            @ApiParam(value = "组织Id", required = true)  String organizationId);

    /**
     * 检查是否在线
     */
    @ApiOperation(value = "检查是否在线", notes = "检查是否在线", position = 3)
    @Authorization(required = true)
    @GetMapping("/isOnline")
    Result<String> isOnline(String number, String activityId, String organizationId);
}
