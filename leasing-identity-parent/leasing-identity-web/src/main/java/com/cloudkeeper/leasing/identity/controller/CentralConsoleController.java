package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "中央控制台", tags = "中央控制台")
@RequestMapping("/centralConsole")
public interface CentralConsoleController {

    /**
     * 数据统计
     */
    @ApiOperation(value = "数据统计", notes = "数据统计", position = 1)
    @PostMapping("/statistics/year{year}")
    Result<CentralConsoleVo> dataStatistics(@PathVariable String year);
}
