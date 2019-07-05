package com.cloudkeeper.leasing.identity.controller;


import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.vo.GisVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(value = "地图", tags = "地图")
@RequestMapping("/gis")
public interface GisController {

    /**
     * 阵地信息
     */
    @ApiOperation(value = "阵地信息", notes = "阵地信息", position = 1)
    @PostMapping("/positionlist")
    Result<List<GisVO>> positionList();
}
