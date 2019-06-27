package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.EasyNVRController;
import com.cloudkeeper.leasing.identity.service.EasyNVRService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EasyNVRControllerImpl implements EasyNVRController {

    private final EasyNVRService easyNVRService;

    @Override
    public Result<Boolean> findOne(String number) {
        if (StringUtils.isEmpty(easyNVRService.catchPic(number))) {
            return  Result.of(false);
        }
        return Result.of(true);
    }
}
