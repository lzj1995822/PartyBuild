package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.PromotionCadres;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村书记拟晋升名单 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PromotionCadresServiceTest {

    /** 村书记拟晋升名单 service */
    @Autowired
    private PromotionCadresService promotionCadresService;

    @Test
    public void saveTest() {
        PromotionCadres promotionCadres = new PromotionCadres();
        promotionCadres = promotionCadresService.save(promotionCadres);
        assertNotNull(promotionCadres.getId());
    }

}