package com.cloudkeeper.leasing.identity.controller;


import com.cloudkeeper.leasing.base.model.CloudResult;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "云平台", tags = "云平台")
@RequestMapping("/cloudStatistics")
public interface CloudStatisticsController {

    @ApiOperation(value = "数据统计", notes = "数据统计", position = 1)
    @PostMapping("/statistics")
    CloudResult<CentralConsoleVo> dataStatistics();

}
