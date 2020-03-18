package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.VillageCadresTerm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村主任任期信息 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VillageCadresTermServiceTest {

    /** 村主任任期信息 service */
    @Autowired
    private VillageCadresTermService villageCadresTermService;

    @Test
    public void saveTest() {
        VillageCadresTerm villageCadresTerm = new VillageCadresTerm();
        villageCadresTerm = villageCadresTermService.save(villageCadresTerm);
        assertNotNull(villageCadresTerm.getId());
    }

}