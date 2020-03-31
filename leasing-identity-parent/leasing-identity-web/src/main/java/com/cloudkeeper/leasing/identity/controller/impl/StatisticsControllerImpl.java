package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.StatisticsController;
import com.cloudkeeper.leasing.identity.dto.villagecadres.ExportDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresStatisticsSearchable;
import com.cloudkeeper.leasing.identity.service.StatisticsService;
import com.cloudkeeper.leasing.identity.vo.StatisticsClassifyVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsListVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsNotIntegerVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsControllerImpl implements StatisticsController {

    private final StatisticsService statisticsService;
    @Override
    public Result<List<StatisticsVO>> getSxStatistics(@PathVariable("districtId") String districtId) {
        List<StatisticsVO> list = statisticsService.getSxStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getAgeStatistics(@PathVariable("districtId")String districtId) {
        List<StatisticsVO> list = statisticsService.getAgeStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getEduStatistics( @PathVariable("districtId") String districtId) {
        List<StatisticsVO> list = statisticsService.getEduStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getcadresTypeStatistics(@PathVariable("districtId") String districtId) {
        List<StatisticsVO> list = statisticsService.getcadresTypeStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getServingYearStatistics( @PathVariable("districtId")String districtId) {
        List<StatisticsVO> list = statisticsService.getServingYearStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getRankStatistics(@PathVariable("districtId")String districtId) {
        List<StatisticsVO> list = statisticsService.getRankStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsClassifyVO>> getSalaryStatistics(@PathVariable("districtId")String districtId) {
        List<StatisticsNotIntegerVO> list = statisticsService.getSalaryStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsNotIntegerVO>> getSalaryStatisticsList(@PathVariable("type")String type) {
        return Result.of(statisticsService.getRewardsStatisticsByType(type));
    }

    @Override
    public Result<List<StatisticsVO>> getPartyStandingStatistics(@PathVariable("districtId")String districtId) {
        List<StatisticsVO> list = statisticsService.getPartyStandingStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsListVO>> getRewardsStatistics(@PathVariable("districtId")String districtId) {
        List<StatisticsListVO> list = statisticsService.getRewardsStatistics(districtId);
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<List<StatisticsVO>> getAllStatistics() {
        List<StatisticsVO> list = statisticsService.getAllStatistics();
        Result r = new Result();
        r.setContent(list);
        return r;
    }

    @Override
    public Result<Object> getCustomStatistics(@RequestBody List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables) {
        return Result.of(statisticsService.getCustomStatistics(villageCadresStatisticsSearchables));
    }

    @Override
    public Result<Object> page(@RequestBody List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables, @RequestParam Integer page, @RequestParam Integer size, Pageable pageable) {
        return Result.of(statisticsService.page(villageCadresStatisticsSearchables,page,size,pageable));
    }

    @Override
    public Result<Object> export(@RequestBody ExportDTO exportDTO) {
        Map<String,String> map = new HashMap<>();
        map.put("file",statisticsService.export(exportDTO));
        return Result.of(map);
    }
}
