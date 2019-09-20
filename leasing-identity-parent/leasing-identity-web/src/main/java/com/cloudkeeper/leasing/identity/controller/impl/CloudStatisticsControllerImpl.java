package com.cloudkeeper.leasing.identity.controller.impl;


import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.CloudResult;
import com.cloudkeeper.leasing.identity.controller.CloudStatisticsController;
import com.cloudkeeper.leasing.identity.service.CentralConsoleService;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * 云平台
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CloudStatisticsControllerImpl implements CloudStatisticsController {

    private final CentralConsoleService centralConsoleService;

    @Authorization(required = false)
    @Override
    public CloudResult<CentralConsoleVo> dataStatistics() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        CentralConsoleVo centralConsoleVo = centralConsoleService.dataStatistics(year);
        return CloudResult.of(centralConsoleVo);
    }
}
