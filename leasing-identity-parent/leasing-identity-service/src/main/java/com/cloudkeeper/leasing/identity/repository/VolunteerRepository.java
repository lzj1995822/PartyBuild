package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.Volunteer;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 志愿者 repository
 * @author asher
 */
@Repository
public interface VolunteerRepository extends BaseRepository<Volunteer> {

}