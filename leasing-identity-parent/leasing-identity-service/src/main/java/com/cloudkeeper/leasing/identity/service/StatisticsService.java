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
    Object getAgeCountByDistrict(String cadresType,String districtId);
    List<StatisticsVO> getAllStatistics();

    Object getCustomStatistics(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables,
                               String cadresType, String districtId);
    Object page(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables,
                String cadresType, Integer page, Integer size, String districtId, Pageable pageable);
    String export(ExportDTO exportDTO,  String cadresType, String districtId);

    // 生成文件
    String generateFileUrl(ExportDTO exportDTO, String resSql);
    List<StatisticsNotIntegerVO> getRewardsStatisticsByType(String type,String cadresType, String districtId);

}
