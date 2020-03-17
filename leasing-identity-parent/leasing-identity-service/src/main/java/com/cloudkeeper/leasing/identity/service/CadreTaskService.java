package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskDTO;
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;
import com.cloudkeeper.leasing.identity.vo.CadreTaskVO;

import java.util.List;

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

    List<CadreTaskObjectVO> getDetailByTaskId(String taskId);

}