package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectDTO;
import com.cloudkeeper.leasing.identity.vo.ParActivityObjectVO;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

/**
 * 任务对象 service
 * @author lxw
 */
public interface ParActivityObjectService extends BaseService<ParActivityObject> {

    void deleteAllByActivityId(String activityId);

    //根据父组织查对应活动
    List<String> findActivityIdsByDistrictCode(String districtCode);

    //根据村组织查对应活动
    List<String> findActivityIdsByOrganizationId(String organizationId);

    ParActivityObject findByOrganizationIdAndActivityId(String organizationId, String activityId);

    //统计数量,计算完成率
    BigDecimal handleActivityCompleteRate(String organizationId);

    // 初始化之前的活动对应活动对象
    void initPerActivity();

    List<ParActivityObjectVO> execute(ParActivityObjectDTO parActivityObjectDTO,Sort sort);
    List<ParActivityObjectVO> executeOver(ParActivityObjectDTO parActivityObjectDTO,Sort sort);

    /**
     * 根据机顶盒编号拿所有当月要执行的任务
     * @param number
     * @return
     */
    List<ParActivityObjectVO> TVIndexDetailList(String number);
}
