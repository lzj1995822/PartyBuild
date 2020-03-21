package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.VillageCadresAnnex;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 村主任信息附件 repository
 * @author yujian
 */
@Repository
public interface VillageCadresAnnexRepository extends BaseRepository<VillageCadresAnnex> {
    void deleteByTypeAndCadresId(String type,String cadresId);
}
