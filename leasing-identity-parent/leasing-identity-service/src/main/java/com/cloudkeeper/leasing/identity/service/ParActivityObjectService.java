package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectDTO;
import com.cloudkeeper.leasing.identity.vo.CloudActivityCunFinishedVO;
import com.cloudkeeper.leasing.identity.vo.CloudActivityRateVO;
import com.cloudkeeper.leasing.identity.vo.ExamScoreDetailVO;
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
    BigDecimal handleActivityCompleteRate(String organizationId, String year);

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

    //活动待审核数量
    Integer waitCheckNumber(String organizationId);

    //考核具体信息
    List<ExamScoreDetailVO> examScoreDetail(String districtName, String year);

    //后台计时器更新正在执行的活动
    void updateIsWorking();

    //云图地图镇当月活动完成率
    List<CloudActivityRateVO> townMonthRate();

    //云图地图村当月活动未完成和已完成数量
    List<CloudActivityCunFinishedVO> cunMonthObject(String attachTo);
}
