package com.cloudkeeper.leasing.identity.controller.impl;


import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.GisController;
import com.cloudkeeper.leasing.identity.service.GisService;
import com.cloudkeeper.leasing.identity.vo.GisVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 地图
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GisControllerImpl implements GisController {

    private final GisService gisService;

    @Override
    public Result<List<GisVO>> positionList() {
        List<GisVO> gisVOS = gisService.queryPosition();
        return Result.of(gisVOS);
    }
}
