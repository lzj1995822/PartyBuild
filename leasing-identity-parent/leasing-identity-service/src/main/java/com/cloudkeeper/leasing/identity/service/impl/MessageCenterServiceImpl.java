package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.repository.*;
import com.cloudkeeper.leasing.identity.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.Optional;

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

    private final ParActivityRepository parActivityRepository;

    //组织
    private final SysDistrictRepository sysDistrictRepository;

    //村干部
    private final VillageCadresRepository villageCadresRepository;

    private final InformationRepository informationRepository;


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
            Optional<ParActivity> byId = parActivityRepository.findById(activityId);
            SysDistrict sysDistrictByDistrictId = sysDistrictRepository.findSysDistrictByDistrictId(districtId);
            messageCenter.setTitle(byId.get().getTitle());
            messageCenter.setDistrictName(sysDistrictByDistrictId.getDistrictName());
            messageCenter.setDistrictId(sysDistrictByDistrictId.getAttachTo());
            messageCenter.setContent("[远教任务] "+messageCenter.getDistrictName()+'"'+messageCenter.getTitle()+'"'+"待审核");
        }
        else if(taskType=="party"){
            Optional<ParActivity> byId = parActivityRepository.findById(activityId);
            if(byId.isPresent()) {
                SysDistrict sysDistrictByDistrictId = sysDistrictRepository.findSysDistrictByDistrictId(districtId);
                messageCenter.setTitle(byId.get().getTitle());
                messageCenter.setDistrictName(sysDistrictByDistrictId.getDistrictName());
                messageCenter.setDistrictId(sysDistrictByDistrictId.getAttachTo());
                messageCenter.setContent("[党建任务] " + messageCenter.getDistrictName() + '"' + messageCenter.getTitle() + '"' + "待审核");
            }
        }
        else if(taskType=="information"){
            Optional<Information> byId = informationRepository.findById(activityId);
            if(byId.isPresent()){
                Information information = byId.get();
                messageCenter.setDistrictId(districtId);
                messageCenter.setTitle(information.getTitle());
                messageCenter.setContent("[通知公告] " +'"'+messageCenter.getTitle()+'"'+"待查收");
            }
        }
        else if(taskType =="checkParty"){
            Optional<ParActivity> activity = parActivityRepository.findById(activityId);
            if(activity.isPresent()){
                String[] strs=districtId.split("-");
                messageCenter.setDistrictId(strs[0]);
                String name = sysDistrictRepository.findSysDistrictByDistrictId(strs[0]).getDistrictName();
                messageCenter.setTitle(activity.get().getTitle());
                if(strs[1].equals("2")){
                    messageCenter.setContent("[党建任务-机关] "+name+"执行的"+'"'+messageCenter.getTitle()+'"'+"活动已审核通过，得分："+strs[2]+"分");
                }else{
                    messageCenter.setContent("[党建任务-机关] "+name+"执行的"+'"'+messageCenter.getTitle()+'"'+"活动审核未通过");
                }
            }
        }
        else{
            Optional<VillageCadres> byId = villageCadresRepository.findById(activityId);
            if(byId.isPresent()){
                VillageCadres villageCadres = byId.get();
                messageCenter.setDistrictId(districtId);
                messageCenter.setType("villageCadres");
                messageCenter.setTitle(villageCadres.getName());
                messageCenter.setContent(taskType);
            }
        }
        return super.save(messageCenter);
    }

}
