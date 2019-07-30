package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.OrganizationRole;
import com.cloudkeeper.leasing.identity.domain.ParActivityFeedback;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.dto.paractivityfeedback.ParActivityFeedbackSearchable;
import com.cloudkeeper.leasing.identity.vo.ParActivityFeedbackVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 移动端执行记录 repository
 * @author lxw
 */
@Repository
public interface ParActivityFeedbackRepository extends BaseRepository<ParActivityFeedback> {

    List<ParActivityFeedback> findAllBySnIdAndUserId(String snId,String userId);

}
