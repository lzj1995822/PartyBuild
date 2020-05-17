package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 村考核指标 repository
 * @author yujian
 */
@Repository
public interface KPIVillageQuotaRepository extends BaseRepository<KPIVillageQuota> {

    void deleteAllByParentDistrictIdAndParentQuotaId(String parentDistrictId,String parentQuotaId);

    void deleteAllByTownQuotaId(String townQuotaId);

    void deleteAllByTownQuotaIdIn(List<String> townQuotaIds);

    @Transactional
    @Modifying
    @Query(value = "update dbo.KPI_village_Quota set score = :score, scoreEnd = :scoreEnd where id = :id", nativeQuery = true)
    Integer updateScoreById(@Param("score") String score,@Param("scoreEnd") String scoreEnd,@Param("id") String id);

    List<KPIVillageQuota> findAllByTownQuotaIdAndQuarter(@Nonnull String townQuotaId, @Nonnull String quarter);

}
