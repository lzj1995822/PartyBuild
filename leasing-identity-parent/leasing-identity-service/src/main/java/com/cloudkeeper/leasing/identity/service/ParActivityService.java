package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivityDTO;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;

import java.time.LocalDateTime;

/**
 * 活动 service
 * @author lxw
 */
public interface ParActivityService extends BaseService<ParActivity> {

    ParActivityVO save(ParActivityDTO parActivityDTO);
    void deleteAll(String id);
    ParActivityVO updateAlarmTime(String id, LocalDateTime localDateTime);
}
