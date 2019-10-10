package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseNoHttpServiceImpl;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import com.cloudkeeper.leasing.identity.repository.SumPerHourRepository;
import com.cloudkeeper.leasing.identity.service.SumPerHourService;
import com.cloudkeeper.leasing.identity.vo.HeatMapVO;
import com.cloudkeeper.leasing.identity.vo.LinkChartVo;
import com.cloudkeeper.leasing.identity.vo.StreamDayVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public Map<String, List> calDayStreamUnit(String interval, String districtId) {
        int defaultInterval = 7;
        String defaultDistrictId = "01";
        if (!StringUtils.isEmpty(interval)) {
            defaultInterval = Integer.valueOf(interval);
        }
        if (!StringUtils.isEmpty(districtId)) {
            defaultDistrictId = districtId;
        }
        //不算当日
        defaultInterval++;
        String sql = " SELECT sum(CASE WHEN temp.type = 'MEMBER_EDUCATION' THEN temp.total ELSE 0 END ) as memberEducation," +
                " sum(CASE WHEN temp.type = 'PARTY_STUDIO' THEN temp.total ELSE 0 END ) as partyStudio," +
                " sum(CASE WHEN temp.type = 'ORGANIZATIONAL_CONFERENCE' THEN temp.total ELSE 0 END ) as organizationalConference," +
                " sum(CASE WHEN temp.type = 'PARTY_CARE' THEN temp.total ELSE 0 END ) as partyCare," +
                " convert(varchar(10),temp.startTime,120) as monthDay" +
                " From (" +
                " SELECT s.total, pinfo.type, s.startTime from Sum_Per_Hour s LEFT JOIN Position_Information pinfo on s.positionId = pinfo.id" +
                " LEFT JOIN SYS_District sdi on sdi.districtId = pinfo.districtId" +
                " LEFT JOIN SYS_District sdip on sdi.attachTo = sdip.districtId" +
                " WHERE sdi.isDelete = 0 and s.startTime >= DATEADD(DD, -" + defaultInterval +", GETDATE()) and s.startTime <= DATEADD(DD, -1, GETDATE())" +
                " and sdi.districtId like '" + defaultDistrictId + "%'" +
                " ) temp" +
                " GROUP BY convert(varchar(10),temp.startTime,120)";
        List<StreamDayVO> allBySql = super.findAllBySql(StreamDayVO.class, sql);
        Map<String, List> map = new HashMap<>();
        map.put("MEMBER_EDUCATION", allBySql.stream().map(StreamDayVO::getMemberEducation).collect(Collectors.toList()));
        map.put("PARTY_STUDIO", allBySql.stream().map(StreamDayVO::getPartyStudio).collect(Collectors.toList()));
        map.put("ORGANIZATIONAL_CONFERENCE", allBySql.stream().map(StreamDayVO::getOrganizationalConference).collect(Collectors.toList()));
        map.put("PARTY_CARE", allBySql.stream().map(StreamDayVO::getPartyCare).collect(Collectors.toList()));
        map.put("monthDay", allBySql.stream().map(StreamDayVO::getMonthDay).collect(Collectors.toList()));
        return map;
    }

    //使用次数计算规则 某日总人流量除以该组织党员数乘以0.5之积 结果大于等于2 算作使用一次。
    //业务逻辑意义是只要该组织下的一半的党员来过（一进一出）该阵地则算作使用一次


}