package com.cloudkeeper.leasing.identity.controller;

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
    @GetMapping("/{number}number")
    Result<Boolean> findOne(@ApiParam(value = "机顶盒编号", required = true) @PathVariable String number);
}
