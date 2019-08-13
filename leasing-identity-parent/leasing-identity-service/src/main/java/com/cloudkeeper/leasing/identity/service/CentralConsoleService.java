package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import org.springframework.lang.NonNull;

public interface CentralConsoleService extends BaseService<BaseEntity> {

    CentralConsoleVo dataStatistics(@NonNull String year);
}
