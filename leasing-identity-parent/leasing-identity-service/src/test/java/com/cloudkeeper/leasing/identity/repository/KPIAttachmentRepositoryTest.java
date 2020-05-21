package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIAttachment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 考核实施佐证材料 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KPIAttachmentRepositoryTest {

    /** 考核实施佐证材料 repository */
    @Autowired
    private KPIAttachmentRepository kPIAttachmentRepository;

    @Test
    public void saveTest() {
        KPIAttachment kPIAttachment = new KPIAttachment();
        kPIAttachment = kPIAttachmentRepository.save(kPIAttachment);
        assertNotNull(kPIAttachment.getId());
    }

}