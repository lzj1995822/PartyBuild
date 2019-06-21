package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.MessageCenterController;
import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import com.cloudkeeper.leasing.identity.dto.messagecenter.MessageCenterDTO;
import com.cloudkeeper.leasing.identity.dto.messagecenter.MessageCenterSearchable;
import com.cloudkeeper.leasing.identity.service.MessageCenterService;
import com.cloudkeeper.leasing.identity.vo.MessageCenterVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 消息中心 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageCenterControllerImpl implements MessageCenterController {

    /** 消息中心 service */
    private final MessageCenterService messageCenterService;

    @Override
    public Result<MessageCenterVO> findOne(@ApiParam(value = "消息中心id", required = true) @PathVariable String id) {
        Optional<MessageCenter> messageCenterOptional = messageCenterService.findOptionalById(id);
        return messageCenterOptional.map(messageCenter -> Result.of(messageCenter.convert(MessageCenterVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<MessageCenterVO> add(@ApiParam(value = "消息中心 DTO", required = true) @RequestBody @Validated MessageCenterDTO messageCenterDTO) {
        MessageCenter messageCenter = messageCenterService.save(messageCenterDTO.convert(MessageCenter.class));
        return Result.ofAddSuccess(messageCenter.convert(MessageCenterVO.class));
    }

    @Override
    public Result<MessageCenterVO> update(@ApiParam(value = "消息中心id", required = true) @PathVariable String id,
        @ApiParam(value = "消息中心 DTO", required = true) @RequestBody @Validated MessageCenterDTO messageCenterDTO) {
        Optional<MessageCenter> messageCenterOptional = messageCenterService.findOptionalById(id);
        if (!messageCenterOptional.isPresent()) {
            return Result.ofLost();
        }
        MessageCenter messageCenter = messageCenterOptional.get();
        BeanUtils.copyProperties(messageCenterDTO, messageCenter);
        messageCenter = messageCenterService.save(messageCenter);
        return Result.ofUpdateSuccess(messageCenter.convert(MessageCenterVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "消息中心id", required = true) @PathVariable String id) {
        messageCenterService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<MessageCenterVO>> list(@ApiParam(value = "消息中心查询条件", required = true) @RequestBody MessageCenterSearchable messageCenterSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<MessageCenter> messageCenterList = messageCenterService.findAll(messageCenterSearchable, sort);
        List<MessageCenterVO> messageCenterVOList = MessageCenter.convert(messageCenterList, MessageCenterVO.class);
        return Result.of(messageCenterVOList);
    }

    @Override
    public Result<Page<MessageCenterVO>> page(@ApiParam(value = "消息中心查询条件", required = true) @RequestBody MessageCenterSearchable messageCenterSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<MessageCenter> messageCenterPage = messageCenterService.findAll(messageCenterSearchable, pageable);
        Page<MessageCenterVO> messageCenterVOPage = MessageCenter.convert(messageCenterPage, MessageCenterVO.class);
        return Result.of(messageCenterVOPage);
    }

}