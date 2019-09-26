package com.cloudkeeper.leasing.identity.schedule;

import com.cloudkeeper.leasing.identity.service.SumPerHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    private SumPerHourService sumPerHourService;

    @Scheduled(cron = "0 0 0-23 * * ?" )
    public void calPeopleStream() {
        sumPerHourService.calPeopleStream();
    }
}
