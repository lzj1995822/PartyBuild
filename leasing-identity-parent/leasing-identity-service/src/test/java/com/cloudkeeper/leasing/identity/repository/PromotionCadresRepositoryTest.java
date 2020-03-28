package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.PromotionCadres;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村书记拟晋升名单 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PromotionCadresRepositoryTest {

    /** 村书记拟晋升名单 repository */
    @Autowired
    private PromotionCadresRepository promotionCadresRepository;

    @Test
    public void saveTest() {
        PromotionCadres promotionCadres = new PromotionCadres();
        promotionCadres = promotionCadresRepository.save(promotionCadres);
        assertNotNull(promotionCadres.getId());
    }

}