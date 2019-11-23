package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.domain.InformationAudit;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.repository.VillageCadresRepository;
import com.cloudkeeper.leasing.identity.service.CadrePositionService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.service.InformationAuditService;
import com.cloudkeeper.leasing.identity.service.MessageCenterService;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * 村干部管理 service
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

    private final SysDistrictService sysDistrictService;

    @Override
    protected BaseRepository<VillageCadres> getBaseRepository() {
        return villageCadresRepository;
    }

    @Autowired
    private MessageCenterService messageCenterService;

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
    @Transactional
    public VillageCadres save(VillageCadresDTO villageCadresDTO) {
        String cadrePositionId = villageCadresDTO.getPost();
        Optional<CadrePosition> optionalById = cadrePositionService.findOptionalById(cadrePositionId);
        if (!optionalById.isPresent()) {
            return null;
        }
        VillageCadres convert = villageCadresDTO.convert(VillageCadres.class);

        SysDistrict byDistrictId = sysDistrictService.findByDistrictId(villageCadresDTO.getDistrictId());
        convert.setParentDistrictId(byDistrictId.getOrgParent());
        convert = super.save(convert);
        CadrePosition cadrePosition = optionalById.get();
        cadrePosition.setCadreId(convert.getId());
        cadrePositionService.save(cadrePosition);
        return convert;
    }

    @Override
    public Boolean submit(VillageCadres villageCadres) {
        villageCadres.setState("1");
        villageCadres = villageCadresRepository.save(villageCadres);

        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setVillageId(villageCadres.getId());

        /*模拟审核通过状态*/
        informationAudit.setStatus(villageCadres.getContact());
        informationAuditService.save(informationAudit);
        return true;
    }

    @Override
    public Boolean virify(@PathVariable("id") String id, @PathVariable("code") String code, @RequestBody  InformationAuditDTO informationAuditDTO2 ) {
        Optional<VillageCadres> byId = villageCadresRepository.findById(id);
        if(!byId.isPresent()){
            return false;
        }

        VillageCadres villageCadres = byId.get();
        String currentState = villageCadres.getState();
        Integer integer = Integer.valueOf(currentState);

        String districtId = new String();
        if(villageCadres.getState().equals("1")){
            districtId = villageCadres.getDistrictId();
        }else if (villageCadres.getState().equals("2")){
            districtId = villageCadres.getDistrictId().substring(0,4);
        }else if(villageCadres.getState().equals("3")){
            districtId=villageCadres.getDistrictId().substring(0,4);
        }
        String checkMsg = new String();
        /*判断前端传来的提交 villageId ,code是否通过*/
        if (code.equals("SUCCESS")) {
            integer++;
            checkMsg = "审核通过意见:";

        } else {
            integer--;
            checkMsg = "审核驳回意见:";
        }
        villageCadres.setState(integer.toString());
        villageCadres = villageCadresRepository.save(villageCadres);

        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setStatus(villageCadres.getState());
        informationAudit.setVillageId(id);
        informationAudit.setAuditAdvice(informationAuditDTO2.getAuditAdvice());
        informationAudit.setAuditor(informationAuditDTO2.getAuditor());

        informationAuditService.save(informationAudit);

        messageCenterService.save(villageCadres.getId(),districtId,"[村书记信息]"+villageCadres.getName()+checkMsg+informationAuditDTO2.getAuditAdvice());
        return true;
    }
}