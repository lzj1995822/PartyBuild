package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysLoginNote;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 系统登录日志 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysLoginNoteServiceTest {

    /** 系统登录日志 service */
    @Autowired
    private SysLoginNoteService sysLoginNoteService;

    @Test
    public void saveTest() {
        SysLoginNote sysLoginNote = new SysLoginNote();
        sysLoginNote = sysLoginNoteService.save(sysLoginNote);
        assertNotNull(sysLoginNote.getId());
    }

}