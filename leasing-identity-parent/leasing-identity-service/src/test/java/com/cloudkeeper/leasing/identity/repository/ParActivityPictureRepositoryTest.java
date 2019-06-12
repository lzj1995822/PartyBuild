package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 手机截图 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParActivityPictureRepositoryTest {

    /** 手机截图 repository */
    @Autowired
    private ParActivityPictureRepository parActivityPictureRepository;

    @Test
    public void saveTest() {
        ParActivityPicture parActivityPicture = new ParActivityPicture();
        parActivityPicture = parActivityPictureRepository.save(parActivityPicture);
        assertNotNull(parActivityPicture.getId());
    }

}