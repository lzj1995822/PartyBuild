package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

public interface CentralConsoleService extends BaseService<BaseEntity> {

    CentralConsoleVo dataStatistics(@NonNull String year);

    List<StatisticsVO> countActivityGroupByType();
}
