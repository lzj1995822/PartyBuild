package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParMember;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 党员管理 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParMemberServiceTest {

    /** 党员管理 service */
    @Autowired
    private ParMemberService parMemberService;

    @Test
    public void saveTest() {
        ParMember parMember = new ParMember();
        parMember = parMemberService.save(parMember);
        assertNotNull(parMember.getId());
    }

}