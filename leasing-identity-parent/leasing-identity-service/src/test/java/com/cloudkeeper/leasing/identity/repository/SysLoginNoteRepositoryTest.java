package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysLoginNote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 系统登录日志 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SysLoginNoteRepositoryTest {

    /** 系统登录日志 repository */
    @Autowired
    private SysLoginNoteRepository sysLoginNoteRepository;

    @Test
    public void saveTest() {
        SysLoginNote sysLoginNote = new SysLoginNote();
        sysLoginNote = sysLoginNoteRepository.save(sysLoginNote);
        assertNotNull(sysLoginNote.getId());
    }

}