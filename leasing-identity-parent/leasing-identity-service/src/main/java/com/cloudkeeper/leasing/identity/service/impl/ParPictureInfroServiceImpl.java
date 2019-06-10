package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import com.cloudkeeper.leasing.identity.repository.ParPictureInfroRepository;
import com.cloudkeeper.leasing.identity.service.ParPictureInfroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 电视截图 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParPictureInfroServiceImpl extends BaseServiceImpl<ParPictureInfro> implements ParPictureInfroService {

    /** 电视截图 repository */
    private final ParPictureInfroRepository parPictureInfroRepository;

    @Override
    protected BaseRepository<ParPictureInfro> getBaseRepository() {
        return parPictureInfroRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("imageURL", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("organizationId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("createTime", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("studyContent", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}
