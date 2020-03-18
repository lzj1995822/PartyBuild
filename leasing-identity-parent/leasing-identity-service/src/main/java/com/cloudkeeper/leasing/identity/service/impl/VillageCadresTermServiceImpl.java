package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.VillageCadresTerm;
import com.cloudkeeper.leasing.identity.repository.VillageCadresTermRepository;
import com.cloudkeeper.leasing.identity.service.VillageCadresTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 村主任任期信息 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageCadresTermServiceImpl extends BaseServiceImpl<VillageCadresTerm> implements VillageCadresTermService {

    /** 村主任任期信息 repository */
    private final VillageCadresTermRepository villageCadresTermRepository;

    @Override
    protected BaseRepository<VillageCadresTerm> getBaseRepository() {
        return villageCadresTermRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("cadresName", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void deleteAllByCadresId(String cadresId) {
        villageCadresTermRepository.deleteAllByCadresId(cadresId);
    }

    @Override
    public VillageCadresTerm findByCadresId(String cadresId) {
        return villageCadresTermRepository.findByCadresId(cadresId);
    }
}
