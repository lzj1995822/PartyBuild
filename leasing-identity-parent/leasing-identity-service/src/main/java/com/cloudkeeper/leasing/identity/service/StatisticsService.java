package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.dto.villagecadres.ExportDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresStatisticsSearchable;
import com.cloudkeeper.leasing.identity.vo.StatisticsListVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsNotIntegerVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatisticsService {

    List<StatisticsVO> getSxStatistics(String districtId,String cadresType);

    List<StatisticsVO> getAgeStatistics(String districtId,String cadresType);

    List<StatisticsVO> getEduStatistics(String districtId,String cadresType);

    List<StatisticsVO> getcadresTypeStatistics(String districtId,String cadresType);
    List<StatisticsVO> getServingYearStatistics(String districtId,String cadresType);

    List<StatisticsVO> getRankStatistics(String districtId,String cadresType);

    List<StatisticsNotIntegerVO> getSalaryStatistics(String districtId,String cadresType);

    List<StatisticsVO> getPartyStandingStatistics(String districtId,String cadresType);

    List<StatisticsListVO> getRewardsStatistics(String districtId,String cadresType);

    List<StatisticsVO> getAllStatistics();

    Object getCustomStatistics(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables);
    Object page(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables, Integer page, Integer size, Pageable pageable);
    String export(ExportDTO exportDTO);

    // 生成文件
    String generateFileUrl(ExportDTO exportDTO, String resSql);
    List<StatisticsNotIntegerVO> getRewardsStatisticsByType(String type,String cadresType);

}
