package com.cloudkeeper.leasing.identity.controller;


import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.vo.StreamDayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 接收公告 controller
 * @author cqh
 */
@Api(value = "基本阵地使用情况echarts图", tags = "基本阵地使用情况echarts图")
@RequestMapping("/positionChart")
public interface PositionChartController {

    @ApiOperation(value = "实时人流量折线图", notes = "实时人流量折线图", position = 1)
    @PostMapping("/realLinkChart")
    Result<Map<String,List>> realLinkChart(String districtId) ;

    @ApiOperation(value = "总人流量折线图", notes = "总人流量折线图", position = 1)
    @PostMapping("/peakLinkChart")
    Result<Map<String,List>> peakLinkChart(String interval,String districtId) ;

    @ApiOperation(value = "阵地使用次数柱状图", notes = "阵地使用次数柱状图", position = 1)
    @PostMapping("/useHistogram")
    Result<Map<String,List>> useHistogram(String interval,String districtId) ;

    @ApiOperation(value = "阵地使用频率雷达图", notes = "阵地使用频率雷达图", position = 1)
    @PostMapping("/frequencyRadar")
    Result<List<StreamDayVO>> frequencyRadar(String interval, String districtId) ;
}
