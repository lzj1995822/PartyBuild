package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.domain.EnvironmentData;

public interface DynamicTaskService {
     Result startCron1(String idStr);
    Result stopCron1(String idStr);
}
