package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectDTO;
import com.cloudkeeper.leasing.identity.repository.ParActivityObjectRepository;
import com.cloudkeeper.leasing.identity.repository.ParActivityPerformRepository;
import com.cloudkeeper.leasing.identity.repository.ParActivityRepository;
import com.cloudkeeper.leasing.identity.repository.SysDistrictRepository;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.ParActivityObjectVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.TownDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 任务对象 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityObjectServiceImpl extends BaseServiceImpl<ParActivityObject> implements ParActivityObjectService {

    /** 任务对象 repository */
    @Autowired
    private ParActivityObjectRepository parActivityObjectRepository;

    @Autowired
    private ParActivityRepository parActivityRepository;

    @Autowired
    private ParActivityPerformRepository parActivityPerformRepository;

    @Autowired
    private SysDistrictService sysDistrictService;

    @Autowired
    private SysDistrictRepository sysDistrictRepository;

    @Autowired
    private SysConfigurationService sysConfigurationService;

    @Autowired
    private DynamicTaskService dynamicTaskService;
    @Override
    protected BaseRepository<ParActivityObject> getBaseRepository() {
        return parActivityObjectRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("isWorking", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("activityId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("organizationId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("attachTo", ExampleMatcher.GenericPropertyMatchers.exact());
    }

    @Override
    public void deleteAllByActivityId(String activityId) {
        parActivityObjectRepository.deleteAllByActivityId(activityId);
    }

    @Override
    public List<String> findActivityIdsByDistrictCode(String districtCode) {
        return parActivityObjectRepository.findActivityIdsByDistrictCode(districtCode);
    }

    @Override
    public List<String> findActivityIdsByOrganizationId(String organizationId) {
        return parActivityObjectRepository.findActivityIdsByOrganizationId(organizationId);
    }

    @Override
    public BigDecimal handleActivityCompleteRate(String organizationId) {
        Integer sum = parActivityObjectRepository.countAllByOrganizationIdStartingWith(organizationId);
        Integer completeSum = parActivityObjectRepository.countAllByOrganizationIdStartingWithAndStatus(organizationId,"2");
        BigDecimal total = new BigDecimal(sum);
        BigDecimal finished = new BigDecimal(completeSum);
        return finished.divide(total, 3,BigDecimal.ROUND_DOWN);
    }

    @Override
    public ParActivityObject findByOrganizationIdAndActivityId(String organizationId, String activityId) {
        return parActivityObjectRepository.findByOrganizationIdAndActivityId(organizationId, activityId);
    }

    @Override
    @Transactional
    public void initPerActivity() {
        List<ParActivity> parActivities = parActivityRepository.findAll();
        List<SysDistrict> sysDistricts = sysDistrictService.findAllByDistrictLevel(3);
        for (ParActivity parActivity: parActivities) {
            for (SysDistrict sysDistrict: sysDistricts) {
                ParActivityObject parActivityObject = new ParActivityObject();
                parActivityObject.setActivityId(parActivity.getId());
                parActivityObject.setOrganizationId(sysDistrict.getDistrictId());
                parActivityObject.setAttachTo(sysDistrict.getAttachTo());
                Optional<ParActivityPerform> byActivityIDAndOrganizationId = parActivityPerformRepository.findByActivityIDAndOrganizationId(parActivity.getId(), sysDistrict.getId());
                if (byActivityIDAndOrganizationId.isPresent()) {
                    ParActivityPerform parActivityPerform = byActivityIDAndOrganizationId.get();
                    parActivityObject.setStatus(parActivityPerform.getStatus());
                    parActivityObject.setSource(parActivityPerform.getSource());
                } else {
                    parActivityObject.setStatus("0");
                }
                super.save(parActivityObject);
            }
        }
    }
    @Override
    @Transactional
    public List<ParActivityObjectVO> execute(ParActivityObjectDTO parActivityObjectDTO, Sort sort){
        if(StringUtils.isEmpty(parActivityObjectDTO.getActivityId()) ||StringUtils.isEmpty(parActivityObjectDTO.getOrganizationId())  ){
            return null;
        }
        //更新Object
        ParActivityObject parActivityObject =parActivityObjectRepository.findByOrganizationIdAndActivityId(parActivityObjectDTO.getOrganizationId(),parActivityObjectDTO.getActivityId());
        parActivityObject.setStatus("1");
        parActivityObject.setIsWorking("1");
        ParActivityObject newObject = super.save(parActivityObject);

        //取得组织长短ID
        SysDistrict districtId = sysDistrictRepository.findSysDistrictByDistrictId(parActivityObjectDTO.getOrganizationId());
        Optional<ParActivityPerform> parActivityPerform = parActivityPerformRepository.findByActivityIDAndOrganizationId(parActivityObjectDTO.getActivityId(),districtId.getId());
        //判断是否perform有值，有：更新SOURCE
        if(parActivityPerform.isPresent()){
            ParActivityPerform parPerform = parActivityPerform.get();
            parPerform.setSource(2);
            parActivityPerformRepository.save(parPerform);
        }//无：新增
        else {
            ParActivityPerform perform = new ParActivityPerform();
            perform.setOrganizationId(districtId.getId());
            perform.setStatus("1");
            perform.setActivityID(parActivityObjectDTO.getActivityId());
            perform.setSource(1);
            parActivityPerformRepository.save(perform);
        }


        SysConfiguration sysConfiguration = sysConfigurationService.findById("b19abd37-df80-4f73-8e8a-de2064720c7e");
        String codeValue = sysConfiguration.getCodeValue();

        ParActivityObjectVO convert = newObject.convert(ParActivityObjectVO.class);
        convert.setCodeValue(codeValue);
        List<ParActivityObjectVO> list = new ArrayList<>();
        list.add(convert);

        return list;
    }
    @Override
    @Transactional
    public List<ParActivityObjectVO> executeOver(ParActivityObjectDTO parActivityObjectDTO, Sort sort) {
        if (StringUtils.isEmpty(parActivityObjectDTO.getActivityId()) || StringUtils.isEmpty(parActivityObjectDTO.getOrganizationId())) {
            return null;
        }
        //更新Object
        ParActivityObject parActivityObject =super.findById(parActivityObjectDTO.getId());
        parActivityObject.setIsWorking("0");
        ParActivityObject newObject = super.save(parActivityObject);
        ParActivityObjectVO convert = newObject.convert(ParActivityObjectVO.class);
        List<ParActivityObjectVO> list = new ArrayList<>();
        list.add(convert);
        return list;
    }
}
