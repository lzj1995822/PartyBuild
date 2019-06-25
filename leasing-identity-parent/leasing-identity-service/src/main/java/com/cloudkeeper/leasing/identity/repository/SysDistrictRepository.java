package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 组织 repository
 * @author lxw
 */
@Repository
public interface SysDistrictRepository extends BaseRepository<SysDistrict> {

    List<SysDistrict> findAllByDistrictLevelNot(Integer districtLevel);

    List<SysDistrict> findAllByDistrictLevel(Integer districtLevel);

    Set<SysDistrict> findAllByAttachTo(String districtId);

    List<SysDistrict> findAllByDistrictId(String districtId);

    SysDistrict findSysDistrictByDistrictId(String districtId);

    /**
     * 逻辑删除
     */
    @Modifying@Transactional
    @Query(value = "update SYS_District set isDelete= ? where id = ?",nativeQuery = true)
    Integer save(Integer isDelete,String id);


}
