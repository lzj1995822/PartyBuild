package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.KPIAttachment;
import com.cloudkeeper.leasing.identity.repository.KPIAttachmentRepository;
import com.cloudkeeper.leasing.identity.service.KPIAttachmentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 考核实施佐证材料 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIAttachmentServiceImpl extends BaseServiceImpl<KPIAttachment> implements KPIAttachmentService {

    /** 考核实施佐证材料 repository */
    private final KPIAttachmentRepository kPIAttachmentRepository;

    @Override
    protected BaseRepository<KPIAttachment> getBaseRepository() {
        return kPIAttachmentRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("quotaLevel", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quarter", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains());
    }


    @Override
    public KPIAttachment findByQuota(@Nonnull String quotaId,@Nonnull String districtId, String quarter, String taskId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIAttachment.class);
        detachedCriteria.add(Restrictions.eq("quotaId", quotaId));
        detachedCriteria.add(Restrictions.eq("districtId", districtId));
        detachedCriteria.add(Restrictions.eq("taskId", taskId));
        if (!StringUtils.isEmpty(quarter)) {
            detachedCriteria.add(Restrictions.eq("quarter", quarter));
        }
        List<KPIAttachment> all = super.findAll(detachedCriteria);
        if (all.size() == 0) {
            return null;
        }
        return all.get(0);
    }

    @Override
    public List<KPIAttachment> findAllByQuota(String quotaId, String districtId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIAttachment.class);
        detachedCriteria.add(Restrictions.eq("quotaId", quotaId));
        detachedCriteria.add(Restrictions.eq("districtId", districtId));
        return super.findAll(detachedCriteria);
    }

    @Override
    public KPIAttachment findByQuotaIdAndDistrictIdAndQuarter(String quotaId, String districtId, String quarter) {
        return kPIAttachmentRepository.findByQuotaIdAndDistrictIdAndQuarter(quotaId, districtId, quarter);
    }

    @Override
    public void deleteAllByQuotaIdAndDistrictIdAndQuarterAndTaskId(String quotaId, String districtId, String quarter, String taskId) {
        kPIAttachmentRepository.deleteAllByQuotaIdAndDistrictIdAndQuarterAndTaskId(quotaId, districtId, quarter, taskId);
    }
}