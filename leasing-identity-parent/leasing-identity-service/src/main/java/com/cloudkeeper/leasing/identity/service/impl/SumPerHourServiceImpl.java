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
    public  Map<String,List> RealLineChart(String districtId) {
        String sql = "SELECT sum(CASE WHEN temp.type = 'MEMBER_EDUCATION' THEN temp.total ELSE 0 END ) as memberEducation, " +
                            "sum(CASE WHEN temp.type = 'PARTY_STUDIO' THEN temp.total ELSE 0 END ) as partyStudio, " +
                            "sum(CASE WHEN temp.type = 'ORGANIZATIONAL_CONFERENCE' THEN temp.total ELSE 0 END ) as organizationalConference, " +
                            "sum(CASE WHEN temp.type = 'PARTY_CARE' THEN temp.total ELSE 0 END ) as partyCare, " +
                            "CONVERT(VARCHAR(100),temp.startTime,121) AS monthDay "+
                "From (SELECT s.startTime ,s.total ,p.type  from Sum_Per_Hour as s LEFT JOIN Position_Information as p ON s.positionId = p.id " +
                "where s.startTime>DATEADD(HOUR, -7, GETDATE()) and districtId like '" + districtId + "%') as temp  " +
                "GROUP BY startTime "+
                "ORDER BY monthDay asc";
        List<StreamDayVO> allBySql = super.findAllBySql(StreamDayVO.class, sql);
        Map<String, List> map = generateCommonMap(allBySql);
        map.put("monthDay", allBySql.stream().map(StreamDayVO::getMonthDay).collect(Collectors.toList()));
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
                " WHERE sdi.isDelete = 0 and s.startTime >= DATEADD(DD, -" + defaultInterval +", GETDATE()) and s.startTime <= DATEADD(DD, -0, GETDATE())" +
                " and sdi.districtId like '" + defaultDistrictId + "%'" +
                " ) temp" +
                " GROUP BY convert(varchar(10),temp.startTime,120)"+
                " order by monthDay asc";
        List<StreamDayVO> allBySql = super.findAllBySql(StreamDayVO.class, sql);
        Map<String, List> map = generateCommonMap(allBySql);
        map.put("monthDay", allBySql.stream().map(StreamDayVO::getMonthDay).collect(Collectors.toList()));
        return map;
    }

    //使用次数计算规则 某日总人流量除以该组织党员数乘以0.5之积 结果大于等于2 算作使用一次。
    //业务逻辑意义是只要该组织下的一半的党员来过（一进一出）该阵地则算作使用一次

    //统计各镇下行政村各阵地使用次数
    @Override
    public Map<String, List> calDayUsingTimes(String interval, String districtId) {
        String sql = handleSql(interval,districtId);
        List<StreamDayVO> allBySql = super.findAllBySql(StreamDayVO.class, sql);
        Map<String, List> map = generateCommonMap(allBySql);
        map.put("districtName", allBySql.stream().map(StreamDayVO::getDistrictName).collect(Collectors.toList()));
        return map;
    }

    // 雷达
    @Override
    public List<StreamDayVO> calDayUsingTimesRadar(String interval, String districtId) {
        String sql = handleSql(interval,districtId);
        List<StreamDayVO> allBySql = super.findAllBySql(StreamDayVO.class, sql);
        return allBySql;
    }

    private Map<String, List> generateCommonMap(List<StreamDayVO> list) {
        Map<String, List> map = new HashMap<>();
        map.put("MEMBER_EDUCATION", list.stream().map(StreamDayVO::getMemberEducation).collect(Collectors.toList()));
        map.put("PARTY_STUDIO", list.stream().map(StreamDayVO::getPartyStudio).collect(Collectors.toList()));
        map.put("ORGANIZATIONAL_CONFERENCE", list.stream().map(StreamDayVO::getOrganizationalConference).collect(Collectors.toList()));
        map.put("PARTY_CARE", list.stream().map(StreamDayVO::getPartyCare).collect(Collectors.toList()));
        return map;
    }

    private String handleSql(String interval, String districtId){
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
        String sql = "SELECT COUNT(CASE WHEN temp2.memberEducation >= 2 THEN 1 ELSE 0 END) AS memberEducation, " +
                "COUNT(CASE WHEN temp2.partyStudio >= 2 THEN 1 ELSE 0 END) AS partyStudio, " +
                "COUNT(CASE WHEN temp2.organizationalConference >= 2 THEN 1 ELSE 0 END) AS organizationalConference, " +
                "COUNT(CASE WHEN temp2.partyCare >= 2 THEN 1 ELSE 0 END) AS partyCare, " +
                "temp2.districtName " +
                "from ( " +
                " SELECT sum(CASE WHEN temp.type = 'MEMBER_EDUCATION' THEN temp.total ELSE 0 END ) as memberEducation, " +
                " sum(CASE WHEN temp.type = 'PARTY_STUDIO' THEN temp.total ELSE 0 END ) as partyStudio, " +
                " sum(CASE WHEN temp.type = 'ORGANIZATIONAL_CONFERENCE' THEN temp.total ELSE 0 END ) as organizationalConference, " +
                " sum(CASE WHEN temp.type = 'PARTY_CARE' THEN temp.total ELSE 0 END ) as partyCare, " +
                " temp.districtName, " +
                " MAX(temp.partyMemberTotal) as partyMemberTotal, " +
                " convert(varchar(10),temp.startTime,120) as monthDay " +
                " From ( " +
                " SELECT s.total, pinfo.type, s.startTime, sdi.districtName, sdi.partyMemberTotal from Sum_Per_Hour s LEFT JOIN Position_Information pinfo on s.positionId = pinfo.id " +
                " LEFT JOIN SYS_District sdi on sdi.districtId = pinfo.districtId " +
                " LEFT JOIN SYS_District sdip on sdi.attachTo = sdip.districtId " +
                " WHERE sdi.isDelete = 0 and s.startTime >= DATEADD(DD, -"+defaultInterval+", GETDATE()) and s.startTime <= DATEADD(DD, -0, GETDATE()) " +
                " and sdi.districtId like '" + defaultDistrictId + "%' " +
                " ) temp GROUP BY temp.districtName, convert(varchar(10),temp.startTime,120) " +
                ") temp2 GROUP BY temp2.districtName";
        if (defaultDistrictId.equals("01")) {
            sql = "Select sum(temp3.memberEducation) as memberEducation, sum(temp3.partyStudio) as partyStudio,  " +
                    "sum(temp3.organizationalConference) as organizationalConference,sum(temp3.partyCare) as partyCare,temp3.townName as districtName from (  " +
                    "SELECT COUNT(CASE WHEN temp2.memberEducation >= 2 THEN 1 ELSE 0 END) AS memberEducation,  " +
                    "COUNT(CASE WHEN temp2.partyStudio >= 2 THEN 1 ELSE 0 END) AS partyStudio,  " +
                    "COUNT(CASE WHEN temp2.organizationalConference >= 2 THEN 1 ELSE 0 END) AS organizationalConference,  " +
                    "COUNT(CASE WHEN temp2.partyCare >= 2 THEN 1 ELSE 0 END) AS partyCare,  " +
                    "temp2.districtName,  " +
                    "temp2.townName  " +
                    "from (  " +
                    " SELECT sum(CASE WHEN temp.type = 'MEMBER_EDUCATION' THEN temp.total ELSE 0 END ) as memberEducation,  " +
                    " sum(CASE WHEN temp.type = 'PARTY_STUDIO' THEN temp.total ELSE 0 END ) as partyStudio,  " +
                    " sum(CASE WHEN temp.type = 'ORGANIZATIONAL_CONFERENCE' THEN temp.total ELSE 0 END ) as organizationalConference,  " +
                    " sum(CASE WHEN temp.type = 'PARTY_CARE' THEN temp.total ELSE 0 END ) as partyCare,  " +
                    " temp.districtName,  " +
                    " temp.townName,  " +
                    " MAX(temp.partyMemberTotal) as partyMemberTotal,  " +
                    " convert(varchar(10),temp.startTime,120) as monthDay  " +
                    " From (  " +
                    " SELECT s.total, pinfo.type, s.startTime, sdi.districtName,sdip.districtName as townName, sdi.partyMemberTotal from Sum_Per_Hour s LEFT JOIN Position_Information pinfo on s.positionId = pinfo.id  " +
                    " LEFT JOIN SYS_District sdi on sdi.districtId = pinfo.districtId  " +
                    " LEFT JOIN SYS_District sdip on sdi.attachTo = sdip.districtId  " +
                    " WHERE sdi.isDelete = 0 and s.startTime >= DATEADD(DD, -"+defaultInterval+", GETDATE()) and s.startTime <= DATEADD(DD, -0, GETDATE())  " +
                    " and sdi.districtId like '01%'  " +
                    " ) temp GROUP BY temp.districtName, convert(varchar(10),temp.startTime,120),temp.townName  " +
                    ") temp2 GROUP BY temp2.districtName,temp2.townName  " +
                    ") temp3 GROUP BY temp3.townName";
        }
        return sql;
    }


}