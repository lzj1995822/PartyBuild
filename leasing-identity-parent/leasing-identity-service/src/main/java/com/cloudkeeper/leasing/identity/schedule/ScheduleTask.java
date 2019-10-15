package com.cloudkeeper.leasing.identity.schedule;

import com.cloudkeeper.leasing.identity.domain.PeopleStream;
import com.cloudkeeper.leasing.identity.repository.PeopleStreamRepository;
import com.cloudkeeper.leasing.identity.service.SumPerHourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    private SumPerHourService sumPerHourService;

    @Autowired
    private PeopleStreamRepository peopleStreamRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 5 0-23 * * ?" )
    public void calPeopleStream() {
        sumPerHourService.calPeopleStream();
    }

    @Scheduled(fixedRate = 2000)
    public void generatePeopleStream() {
        PeopleStream peopleStream = new PeopleStream();
        peopleStream.setAmount(1);
        int randomHardwareId = 1 + (int)(Math.random() * 656);
        String locationCode = String.valueOf(randomHardwareId);
        peopleStream.setLocationCode(locationCode);
        peopleStreamRepository.save(peopleStream);
        logger.info("地址编码为" + locationCode + "有人通过");
    }

}
