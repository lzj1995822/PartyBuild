package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.StatisticsController;
import com.cloudkeeper.leasing.identity.service.StatisticsService;
import com.cloudkeeper.leasing.identity.vo.StatisticsClassifyVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsListVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
import com.cloudkeeper.leasing.identity.vo.SumPerHourVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsControllerImpl implements StatisticsController {

    private final StatisticsService statisticsService;
    @Override
    public Result<List<StatisticsVO>> getSxStatistics(String districtId) {
        List<StatisticsVO> list = statisticsService.getSxStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getAgeStatistics(String districtId) {
        List<StatisticsVO> list = statisticsService.getAgeStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getEduStatistics(String districtId) {
        List<StatisticsVO> list = statisticsService.getEduStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getcadresTypeStatistics(String districtId) {
        List<StatisticsVO> list = statisticsService.getcadresTypeStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getServingYearStatistics(String districtId) {
        List<StatisticsVO> list = statisticsService.getServingYearStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getRankStatistics(String districtId) {
        List<StatisticsVO> list = statisticsService.getRankStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsClassifyVO>> getSalaryStatistics(String districtId) {
        List<StatisticsClassifyVO> list = statisticsService.getSalaryStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getPartyStandingStatistics(String districtId) {
        List<StatisticsVO> list = statisticsService.getPartyStandingStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsListVO>> getRewardsStatistics(String districtId) {
        List<StatisticsListVO> list = statisticsService.getRewardsStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }
}
