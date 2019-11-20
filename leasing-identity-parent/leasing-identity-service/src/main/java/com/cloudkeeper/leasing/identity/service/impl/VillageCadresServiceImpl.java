package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.identity.domain.InformationAudit;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.repository.VillageCadresRepository;
import com.cloudkeeper.leasing.identity.service.CadrePositionService;
import com.cloudkeeper.leasing.identity.service.InformationAuditService;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 村干部管理 service
 *
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageCadresServiceImpl extends BaseServiceImpl<VillageCadres> implements VillageCadresService {

    /**
     * 村干部管理 repository
     */
    private final VillageCadresRepository villageCadresRepository;
    private final InformationAuditService informationAuditService;

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

    @Override
    public Boolean submit(VillageCadres villageCadres) {
        villageCadres.setContact("1");
        villageCadres = villageCadresRepository.save(villageCadres);

        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setVillageId(villageCadres.getId());

        /*模拟审核通过状态*/
        informationAudit.setStatus(villageCadres.getContact());
        informationAuditService.save(informationAudit);
        return true;
    }

    @Override
    public Boolean virify(String villageId, String code) {
        Optional<VillageCadres> byId = villageCadresRepository.findById(villageId);
        if(!byId.isPresent()){
            return false;
        }

        VillageCadres villageCadres = byId.get();
        String currentState = villageCadres.getState();
        Integer integer = Integer.valueOf(currentState);

        /*判断前端传来的提交 villageId ,code是否通过*/
        if (code.equals("success")) {
            integer++;
        } else {
            integer--;
        }
        villageCadres.setState(integer.toString());
        villageCadres = villageCadresRepository.save(villageCadres);

        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setStatus(villageCadres.getState());
        informationAudit.setVillageId(villageId);
        informationAuditService.save(informationAudit);
        return true;
    }
}