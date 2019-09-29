package com.cloudkeeper.leasing.identity.socket;

import com.cloudkeeper.leasing.identity.domain.PeopleStream;
import com.cloudkeeper.leasing.identity.repository.PeopleStreamRepository;
import com.cloudkeeper.leasing.identity.service.PeopleStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.DatagramPacket;

public class TaskHandler implements Runnable {

    private static final Integer IS_PASS_INDEX = 4;

    private static final Integer LOCATION_CODE_INDEX = 0;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private DatagramPacket datagramPacket;

    public void setDatagramPacket(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
    }

    private PeopleStreamRepository peopleStreamRepository;

    public PeopleStreamRepository getPeopleStreamRepository() {
        return peopleStreamRepository;
    }

    public void setPeopleStreamRepository(PeopleStreamRepository peopleStreamRepository) {
        this.peopleStreamRepository = peopleStreamRepository;
    }

    @Override
    public void run() {
        byte[] receiveBytes = datagramPacket.getData();
        if (receiveBytes[IS_PASS_INDEX] == 1) {
            //有人经过,根据地址code查所关联的阵地
            PeopleStream peopleStream = new PeopleStream();
            peopleStream.setAmount(1);
            String locationCode = String.valueOf ((int)receiveBytes[LOCATION_CODE_INDEX]);
            peopleStream.setLocationCode(locationCode);
            peopleStreamRepository.save(peopleStream);
            logger.info("地址编码为" + locationCode + "有人通过");
        }
    }
}
