package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIAttachment;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 考核实施佐证材料 repository
 * @author asher
 */
@Repository
public interface KPIAttachmentRepository extends BaseRepository<KPIAttachment> {

    void deleteAllByQuotaIdAndDistrictIdAndQuarterAndTaskId(String quotaId, String districtId, String quarter, String taskId);
}