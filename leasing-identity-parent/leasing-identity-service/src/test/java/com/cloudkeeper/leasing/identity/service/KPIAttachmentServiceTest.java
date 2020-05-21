package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIAttachment;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 考核实施佐证材料 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KPIAttachmentServiceTest {

    /** 考核实施佐证材料 service */
    @Autowired
    private KPIAttachmentService kPIAttachmentService;

    @Test
    public void saveTest() {
        KPIAttachment kPIAttachment = new KPIAttachment();
        kPIAttachment = kPIAttachmentService.save(kPIAttachment);
        assertNotNull(kPIAttachment.getId());
    }

}