package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.repository.ParActivityObjectRepository;
import com.cloudkeeper.leasing.identity.repository.ParActivityPerformRepository;
import com.cloudkeeper.leasing.identity.repository.ParActivityRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityObjectService;
import com.cloudkeeper.leasing.identity.service.ParActivityPerformService;
import com.cloudkeeper.leasing.identity.service.ParActivityService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 任务对象 service
 * @author lxw
 */
@Service
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
    public BigDecimal countAllByOrganizationIdStartingWithAndStatus(String organizationId, String status) {
        Integer sum = parActivityObjectRepository.countAllByOrganizationIdStartingWithAndStatus(organizationId,"0");
        Integer completeSum = parActivityObjectRepository.countAllByOrganizationIdStartingWithAndStatus(organizationId, "2");
        BigDecimal total = new BigDecimal(sum);
        BigDecimal finished = new BigDecimal(completeSum);
        return finished.divide(total, 3,BigDecimal.ROUND_DOWN);
    }

    @Override
    public ParActivityObject findByOrganizationIdAndActivityId(String organizationId, String activityId) {
        return parActivityObjectRepository.findByOrganizationIdAndActivityId(organizationId, activityId);
    }

    @Override
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

}
