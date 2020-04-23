package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIEvaluation;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 综合评议 repository
 * @author yujian
 */
@Repository
public interface KPIEvaluationRepository extends BaseRepository<KPIEvaluation> {

    void  deleteByTypeAndTaskIdAndDistrictId(String type, String taskId, String districtId);

}
