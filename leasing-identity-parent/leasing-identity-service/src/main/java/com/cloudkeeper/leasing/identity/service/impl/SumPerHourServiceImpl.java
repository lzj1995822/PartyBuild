package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseNoHttpServiceImpl;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import com.cloudkeeper.leasing.identity.repository.SumPerHourRepository;
import com.cloudkeeper.leasing.identity.service.SumPerHourService;
import com.cloudkeeper.leasing.identity.vo.HeatMapVO;
import com.cloudkeeper.leasing.identity.vo.LinkChartVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 每小时人流量 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SumPerHourServiceImpl extends BaseNoHttpServiceImpl<SumPerHour> implements SumPerHourService {

    /** 每小时人流量 repository */
    private final SumPerHourRepository sumPerHourRepository;

    @Override
    protected BaseRepository<SumPerHour> getBaseRepository() {
        return sumPerHourRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("locationCode", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void calPeopleStream() {
        List<Object[]> allBySql = sumPerHourRepository.calPerHour();
        for (Object[] item : allBySql) {
            SumPerHour sumPerHour = new SumPerHour();
            sumPerHour.setTotal((int)item[0]);
            sumPerHour.setPositionId(String.valueOf(item[1]));
            sumPerHour.setStartTime(((Timestamp)item[2]).toLocalDateTime());
            sumPerHour.setEndTime(((Timestamp)item[3]).toLocalDateTime());
            sumPerHour.setLocationCode(String.valueOf(item[4]));
            sumPerHourRepository.save(sumPerHour);
        }

    }

    public List<HeatMapVO> getHeatMapData(LocalDateTime startTime, LocalDateTime endTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT sd.districtId, sd.districtName, SUM(sph.total) as total, sd.location FROM Sum_Per_Hour sph LEFT JOIN Position_Information pin on sph.positionId = pin.id " +
                " LEFT JOIN SYS_District sd on sd.districtId = pin.districtId" +
                " WHERE 1=1 AND sph.endTime >= '" + startTime.format(dateTimeFormatter) + "' and " +
                " sph.endTime < '" + endTime.format(dateTimeFormatter) + "'" +
                " GROUP BY sd.districtId,sd.districtName,sd.location";
        List<HeatMapVO> allBySql = super.findAllBySql(HeatMapVO.class, sql);
        return allBySql;
    }

    @Override
    public  Map<String,List<Integer>> RealLinkChart(String districtId) {
        String sql = "SELECT s.positionId as positionId,s.startTime as startTime,s.endTime as endTime,s.total as total,p.type as type,p.districtId as districtId from Sum_Per_Hour as s LEFT JOIN Position_Information as p ON s.positionId = p.id " +
                "where s.startTime>DATEADD(HOUR, -313, GETDATE()) and districtId = '010806' " +
                "ORDER BY type asc , startTime asc";
        List<LinkChartVo> allBySql = super.findAllBySql(LinkChartVo.class, sql);
        Map<String,List<Integer>> map  = new LinkedHashMap<>();
        for(int i=0;i<allBySql.size();i++){
            LinkChartVo item = allBySql.get(i);
            String key = item.getType();
            if(map.containsKey(key)) {
                map.get(key).add(item.getTotal());
            } else{
                List temp = new ArrayList();
                temp.add(item.getTotal());
                map.put(key,temp);
            }
        }
        return map;
    }
}