package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 任务执行记录 service
 * @author lxw
 */
public interface ParActivityPerformService extends BaseService<ParActivityPerform> {
//    Page<ParActivityPerform> listAll(String activityId, String orgId, Pageable pageable);
    List<PassPercentVO> perecent(String activityId);
}
