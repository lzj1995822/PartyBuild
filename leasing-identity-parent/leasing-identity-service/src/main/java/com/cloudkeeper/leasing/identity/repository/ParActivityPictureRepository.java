package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 手机截图 repository
 * @author lxw
 */
@Repository
public interface ParActivityPictureRepository extends BaseRepository<ParActivityPicture> {

    /**
     * 根据acid删除
     */
    void deleteAllByActivityID(String activityId);

}
