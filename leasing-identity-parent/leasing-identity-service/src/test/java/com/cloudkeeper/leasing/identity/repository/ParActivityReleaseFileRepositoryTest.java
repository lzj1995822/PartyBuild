package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityReleaseFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 发布任务上传文件 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParActivityReleaseFileRepositoryTest {

    /** 发布任务上传文件 repository */
    @Autowired
    private ParActivityReleaseFileRepository parActivityReleaseFileRepository;

    @Test
    public void saveTest() {
        ParActivityReleaseFile parActivityReleaseFile = new ParActivityReleaseFile();
        parActivityReleaseFile = parActivityReleaseFileRepository.save(parActivityReleaseFile);
        assertNotNull(parActivityReleaseFile.getId());
    }

}