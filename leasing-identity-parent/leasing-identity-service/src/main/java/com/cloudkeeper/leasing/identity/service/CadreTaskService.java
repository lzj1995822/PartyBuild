package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskDTO;
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;

import java.util.List;
import java.util.Map;

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

    Map<String,List> activitiesCompletion(String year, String objectType,String taskType);

}
