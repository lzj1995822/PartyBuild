package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.EasyNVRController;
import com.cloudkeeper.leasing.identity.service.EasyNVRService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EasyNVRControllerImpl implements EasyNVRController {

    private final EasyNVRService easyNVRService;

    @Override
    @Authorization(required = false)
    public Result<String> findOne(String number,String activityId, String organizationId) {
        String pathUri = easyNVRService.catchPic(number,activityId,organizationId);
        if (!StringUtils.isEmpty(pathUri)) {
            return  Result.of(200,"抓图成功",pathUri);
        }
            return Result.of(200, "抓图失败");

    }


    @Override
    @Authorization(required = false)
    public Result<String> isOnline(String number,String activityId, String organizationId) {
        Boolean isOnline = easyNVRService.isOnlineByUrl(number,activityId,organizationId);
        if (isOnline) {
            return  Result.of(200,"在线");
        }
        return Result.of(500, "不在线");

    }
}
