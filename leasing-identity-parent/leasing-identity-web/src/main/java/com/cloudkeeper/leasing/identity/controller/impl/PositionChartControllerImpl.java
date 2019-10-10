package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.PositionChartController;
import com.cloudkeeper.leasing.identity.service.SumPerHourService;
import com.cloudkeeper.leasing.identity.vo.StreamDayVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 基本阵地使用情况echarts图表 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PositionChartControllerImpl implements PositionChartController {

    private final SumPerHourService sumPerHourService;

    @Override
    public Result<Map<String, List>> realLinkChart(String districtId) {
        Map<String, List> maps = sumPerHourService.RealLinkChart(districtId);
        return Result.of(maps);
    }

    @Override
    public Result<Map<String, List>> peakLinkChart(String interval,String districtId) {
        Map<String, List> stringListMap = sumPerHourService.calDayStreamUnit(interval, districtId);
        return Result.of(stringListMap);
    }

    @Override
    public Result<Map<String, List>> useHistogram(String interval, String districtId) {
        Map<String, List> stringListMap = sumPerHourService.calDayUsingTimes(interval, districtId);
        return Result.of(stringListMap);
    }

    @Override
    public Result<List<StreamDayVO>> frequencyRadar(String interval, String districtId) {
        List<StreamDayVO> streamDayVOS = sumPerHourService.calDayUsingTimesRadar(interval, districtId);
        return Result.of(streamDayVOS);
    }
}
