package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskDTO;

/**
 * 村书记模块任务 service
 * @author asher
 */
public interface CadreTaskService extends BaseService<CadreTask> {

    CadreTask save(CadreTaskDTO cadreTaskDTO);

    CadreTask getCurrentBaseInfoTask();

    CadreTask getCurrentReviewTask();

    CadreTask getCurrentLevelJudgeTask();

    CadreTask getCurrentTaskByType(String type);

    void deleteById(String id);

}