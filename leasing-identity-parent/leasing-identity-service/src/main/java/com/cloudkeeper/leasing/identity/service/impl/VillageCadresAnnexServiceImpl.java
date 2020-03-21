package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.VillageCadresAnnex;
import com.cloudkeeper.leasing.identity.repository.VillageCadresAnnexRepository;
import com.cloudkeeper.leasing.identity.service.VillageCadresAnnexService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * 村主任信息附件 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageCadresAnnexServiceImpl extends BaseServiceImpl<VillageCadresAnnex> implements VillageCadresAnnexService {

    /** 村主任信息附件 repository */
    private final VillageCadresAnnexRepository villageCadresAnnexRepository;

    @Override
    protected BaseRepository<VillageCadresAnnex> getBaseRepository() {
        return villageCadresAnnexRepository;
    }

    @Nonnull
    @Override
    public VillageCadresAnnex save(@Nonnull VillageCadresAnnex entity) {
        villageCadresAnnexRepository.deleteByTypeAndCadresId(entity.getType(),entity.getCadresId());
        return super.save(entity);
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("filePath", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}
