package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.dto.villagecadres.ExportDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresStatisticsSearchable;
import com.cloudkeeper.leasing.identity.vo.StatisticsClassifyVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsListVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StatisticsService {

    List<StatisticsVO> getSxStatistics(String districtId);

    List<StatisticsVO> getAgeStatistics(String districtId);

    List<StatisticsVO> getEduStatistics(String districtId);

    List<StatisticsVO> getcadresTypeStatistics(String districtId);
    List<StatisticsVO> getServingYearStatistics(String districtId);

    List<StatisticsVO> getRankStatistics(String districtId);

    List<StatisticsClassifyVO> getSalaryStatistics(String districtId);

    List<StatisticsVO> getPartyStandingStatistics(String districtId);

    List<StatisticsListVO> getRewardsStatistics(String districtId);

    List<StatisticsVO> getAllStatistics();

    Object getCustomStatistics(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables);
    Object page(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables, Integer page, Integer size, Pageable pageable);
    String export(ExportDTO exportDTO);
}
