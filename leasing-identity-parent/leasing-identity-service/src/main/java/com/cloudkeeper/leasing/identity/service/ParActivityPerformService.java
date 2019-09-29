package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.paractivityperform.ParActivityPerformDTO;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import com.cloudkeeper.leasing.identity.vo.TownDetailVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 任务执行记录 service
 * @author lxw
 */
public interface ParActivityPerformService extends BaseService<ParActivityPerform> {
//    Page<ParActivityPerform> listAll(String activityId, String orgId, Pageable pageable);
    List<PassPercentVO> percent(String activityId);
    List<TownDetailVO> townDetail(String activityId, String town);
    ParActivityPerformVO check(ParActivityPerformDTO parActivityPerformDTO);
    Integer countAll(String districtId, String year);

    Optional<ParActivityPerform> findByActivityIDAndOrganizationId(String activityId, String organizationId);

    Integer countAllByStatus(String status);
}
