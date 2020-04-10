package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.CentralConsoleController;
import com.cloudkeeper.leasing.identity.service.CentralConsoleService;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 中央控制台
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CentralConsoleControllerImpl implements CentralConsoleController {


    private final CentralConsoleService centralConsoleService;

    @Authorization(required = true)
    @Override
    public Result<CentralConsoleVo> dataStatistics(@PathVariable String year) {
        CentralConsoleVo centralConsoleVo = centralConsoleService.dataStatistics(year);
        return Result.of(centralConsoleVo);
    }

    @GetMapping("/countActivityGroupByType")
    public Result<List<StatisticsVO>> countActivityGroupByType() {
        return Result.of(centralConsoleService.countActivityGroupByType());
    }
}
