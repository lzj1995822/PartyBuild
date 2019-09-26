package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseNoHttpServiceImpl;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import com.cloudkeeper.leasing.identity.repository.SumPerHourRepository;
import com.cloudkeeper.leasing.identity.service.SumPerHourService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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
}