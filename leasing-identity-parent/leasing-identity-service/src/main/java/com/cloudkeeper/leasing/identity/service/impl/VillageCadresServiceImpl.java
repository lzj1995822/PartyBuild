package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import com.cloudkeeper.leasing.identity.repository.VillageCadresRepository;
import com.cloudkeeper.leasing.identity.service.CadrePositionService;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 村干部管理 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageCadresServiceImpl extends BaseServiceImpl<VillageCadres> implements VillageCadresService {

    /** 村干部管理 repository */
    private final VillageCadresRepository villageCadresRepository;

    private final CadrePositionService cadrePositionService;

    @Override
    protected BaseRepository<VillageCadres> getBaseRepository() {
        return villageCadresRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("sex", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nation", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("health", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nativePlace", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("birthPlace", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("education", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("identityCard", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("postExperience", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public Long countAllByDistrictId(String districtId) {
        Long aLong = villageCadresRepository.countAllByDistrictIdStartingWith(districtId);
        return aLong;
    }

    @Override
    public void initPost() {
        List<VillageCadres> all = villageCadresRepository.findAll();
        all.forEach(item -> {
            CadrePosition cadrePosition = new CadrePosition();
            cadrePosition.setDistrictId(item.getDistrictId());
            cadrePosition.setCadreId(item.getId());
            if (!StringUtils.isEmpty(item.getSysDistrict())) {
                cadrePosition.setPost(item.getSysDistrict().getDistrictName() + item.getDuty());
            }
            cadrePositionService.save(cadrePosition);
        });

    }
}