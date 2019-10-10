package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.HeatMapVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 每小时人流量 service
 * @author asher
 */
public interface SumPerHourService extends BaseService<SumPerHour> {

    void calPeopleStream();

    List<HeatMapVO> getHeatMapData(LocalDateTime startTime, LocalDateTime endTime);

    Map<String,List<Integer>> RealLinkChart(String districtId);

}