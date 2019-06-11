package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 电视截图 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParPictureInfroRepositoryTest {

    /** 电视截图 repository */
    @Autowired
    private ParPictureInfroRepository parPictureInfroRepository;

    @Test
    public void saveTest() {
        ParPictureInfro parPictureInfro = new ParPictureInfro();
        parPictureInfro = parPictureInfroRepository.save(parPictureInfro);
        assertNotNull(parPictureInfro.getId());
    }

}