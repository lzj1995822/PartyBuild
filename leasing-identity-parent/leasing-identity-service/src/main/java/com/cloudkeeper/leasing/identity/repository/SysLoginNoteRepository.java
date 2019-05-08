package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysLoginNote;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统登录日志 repository
 * @author cqh
 */
@Repository
public interface SysLoginNoteRepository extends BaseRepository<SysLoginNote> {

}