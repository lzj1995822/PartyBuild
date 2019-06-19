package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.CentralConsoleController;
import com.cloudkeeper.leasing.identity.service.CentralConsoleService;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中央控制台
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CentralConsoleControllerImpl implements CentralConsoleController {


    private final CentralConsoleService centralConsoleService;

    @Override
    public Result<CentralConsoleVo> dataStatistics() {
        CentralConsoleVo centralConsoleVo = centralConsoleService.dataStatistics();
        return Result.of(centralConsoleVo);
    }
}
