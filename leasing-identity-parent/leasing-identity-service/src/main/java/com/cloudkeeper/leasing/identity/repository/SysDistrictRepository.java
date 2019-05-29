package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织 repository
 * @author lxw
 */
@Repository
public interface SysDistrictRepository extends BaseRepository<SysDistrict> {

    List<SysDistrict> findAllByDistrictLevelNot(Integer districtLevel);

}