package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.base.service.BaseService;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * 村考核指标 service
 * @author yujian
 */
public interface KPIVillageQuotaService extends BaseService<KPIVillageQuota> {

    void deleteAllByParentDistrictIdAndParentQuotaId(String parentDistrictId,String parentQuotaId);

    List<Map<String, Object>> buildCommonWorkData(String districtId, String taskId, String reviewTaskId, String parentQuotaId, String quarter, String makeQuotaDistrictId);

    List<Map<String, Object>> buildCommonData(String districtId, String taskId, String reviewTaskId, String rootQuotaId, String makeQuotaDistrictId);

    List<Map<String, Object>> buildWatchQuotaData(String districtId, String taskId, String reviewTaskId, String taskYear, String parentQuotaId, String makeQuotaDistrictId);

    List<Map<String, Object>> buildCommentQuotaData(String districtId, String taskId, String reviewTaskId, String taskYear, String parentQuotaId, String makeQuotaDistrictId);

    void deleteAllByTownQuotaId(String townQuotaId);

    void deleteAllByTownQuotaIdIn(List<String> townQuotaIds);

    void updateScoreById(@Nonnull String score, @Nonnull String scoreEnd, @Nonnull String id);

    List<KPIVillageQuota> findAllByTownQuotaIdAndQuarter(@Nonnull String townQuotaId, @Nonnull String quarter);

    List<KPIVillageQuota> findAllByParentQuotaId(String parentQuotaId);
}
