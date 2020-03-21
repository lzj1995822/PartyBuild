package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.VillageCadresAnnex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村主任信息附件 repository 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VillageCadresAnnexRepositoryTest {

    /** 村主任信息附件 repository */
    @Autowired
    private VillageCadresAnnexRepository villageCadresAnnexRepository;

    @Test
    public void saveTest() {
        VillageCadresAnnex villageCadresAnnex = new VillageCadresAnnex();
        villageCadresAnnex = villageCadresAnnexRepository.save(villageCadresAnnex);
        assertNotNull(villageCadresAnnex.getId());
    }

}