package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityReleaseFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 发布任务上传文件 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParActivityReleaseFileServiceTest {

    /** 发布任务上传文件 service */
    @Autowired
    private ParActivityReleaseFileService parActivityReleaseFileService;

    @Test
    public void saveTest() {
        ParActivityReleaseFile parActivityReleaseFile = new ParActivityReleaseFile();
        parActivityReleaseFile = parActivityReleaseFileService.save(parActivityReleaseFile);
        assertNotNull(parActivityReleaseFile.getId());
    }

}