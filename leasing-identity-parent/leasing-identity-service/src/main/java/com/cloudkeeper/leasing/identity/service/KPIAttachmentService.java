package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIAttachment;
import com.cloudkeeper.leasing.base.service.BaseService;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 考核实施佐证材料 service
 * @author asher
 */
public interface KPIAttachmentService extends BaseService<KPIAttachment> {

    KPIAttachment findByQuota(String quotaId, String districtId, String quarter, String taskId);

    List<KPIAttachment> findAllByQuota(String quotaId, String districtId);

    KPIAttachment findByQuotaIdAndDistrictIdAndQuarter(String quotaId, String districtId, String quarter);

    void deleteAllByQuotaIdAndDistrictIdAndQuarterAndTaskId(String quotaId, String districtId, String quarter, String taskId);
}