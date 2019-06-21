package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.repository.MessageCenterRepository;
import com.cloudkeeper.leasing.identity.repository.SysDistrictRepository;
import com.cloudkeeper.leasing.identity.service.MessageCenterService;
import com.cloudkeeper.leasing.identity.service.ParActivityObjectService;
import com.cloudkeeper.leasing.identity.service.ParActivityService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;

/**
 * 消息中心 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageCenterServiceImpl extends BaseServiceImpl<MessageCenter> implements MessageCenterService {

    /** 消息中心 repository */
    private final MessageCenterRepository messageCenterRepository;

    //活动
    private final ParActivityObjectService parActivityObjectService;

    //组织
    private final SysDistrictRepository sysDistrictRepository;


    @Override
    protected BaseRepository<MessageCenter> getBaseRepository() {
        return messageCenterRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Nonnull
    @Override
    public MessageCenter save(@Nonnull String activityId, String districtId, String taskType) {
        MessageCenter messageCenter = new MessageCenter();
        messageCenter.setBusinessId(activityId);
        messageCenter.setType(taskType);
        messageCenter.setIsRead(0);
        if(taskType=="distLearning"){
            SysDistrict sysDistrictByDistrictId = sysDistrictRepository.findSysDistrictByDistrictId(districtId);
            messageCenter.setDistrictName(sysDistrictByDistrictId.getDistrictName());
            messageCenter.setDistrictId(sysDistrictByDistrictId.getAttachTo());
            messageCenter.setContent("[远教任务] "+messageCenter.getDistrictName()+'"'+messageCenter.getTitle()+'"'+"待审核");
        }
        if(taskType=="party"){
            SysDistrict sysDistrictByDistrictId = sysDistrictRepository.findSysDistrictByDistrictId(districtId);
            messageCenter.setDistrictName(sysDistrictByDistrictId.getDistrictName());
            messageCenter.setDistrictId(sysDistrictByDistrictId.getAttachTo());
            messageCenter.setContent("[党建任务] "+messageCenter.getDistrictName()+'"'+messageCenter.getTitle()+'"'+"待审核");
        }
        return super.save(messageCenter);
    }

}