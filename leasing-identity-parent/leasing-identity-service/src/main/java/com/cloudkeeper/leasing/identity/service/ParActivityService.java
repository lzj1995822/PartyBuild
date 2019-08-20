package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivityDTO;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivitySearchable;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
import com.cloudkeeper.leasing.identity.vo.TVIndexVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 活动 service
 * @author lxw
 */
public interface ParActivityService extends BaseService<ParActivity> {

    ParActivityVO save(ParActivityDTO parActivityDTO);
    void deleteAll(String id);
    ParActivityVO updateAlarmTime(String id, LocalDateTime localDateTime);

    //更新进度
    ParActivity updateProgress(String activityId);

    // 处理不同角色进来所看到的的活动内容不一样
    Page<ParActivity> handleDifferentRole(ParActivitySearchable parActivitySearchable, Pageable pageable);

    /**
     * 电视端首页
     * @return
     */
    Result<TVIndexVO> tvIndex();

    Result<Map<String,List>> activityCompletion(String year, String districtId, String objectType,String districtType);
}
