package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskDTO;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictSearchable;
import com.cloudkeeper.leasing.identity.repository.CadreTaskRepository;
import com.cloudkeeper.leasing.identity.service.CadreTaskObjectService;
import com.cloudkeeper.leasing.identity.service.CadreTaskService;
import com.cloudkeeper.leasing.identity.service.MessageCenterService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 村书记模块任务 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadreTaskServiceImpl extends BaseServiceImpl<CadreTask> implements CadreTaskService {

    private static final String BASE_INFO_TASK = "基本信息更新";

    private static final String REVIEW_TASK = "年度考核";

    private static final String Level_JUDGE_TASK = "职级评定";

    /** 村书记模块任务 repository */
    private final CadreTaskRepository cadreTaskRepository;

    private final SysDistrictService sysDistrictService;

    private final CadreTaskObjectService cadreTaskObjectService;

    private final MessageCenterService messageCenterService;

    @Override
    protected BaseRepository<CadreTask> getBaseRepository() {
        return cadreTaskRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("score", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("attach", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    @Transactional
    public CadreTask save(CadreTaskDTO cadreTaskDTO) {
        CadreTask cadreTask = super.save(cadreTaskDTO.convert(CadreTask.class));
        String type = cadreTaskDTO.getType();

        SysDistrictSearchable sysDistrictSearchable = new SysDistrictSearchable();
        if (BASE_INFO_TASK.equals(type) || Level_JUDGE_TASK.equals(type)) {
            sysDistrictSearchable.setDistrictType("Party");
            sysDistrictSearchable.setDistrictLevel(3);
        } else if (REVIEW_TASK.equals(type)) {
            sysDistrictSearchable.setDistrictType("Party");
        } else {
            return null;
        }
        List<SysDistrict> all = sysDistrictService.findAll(sysDistrictSearchable);
        for (SysDistrict item : all) {
            CadreTaskObject cadreTaskObject = new CadreTaskObject();
            cadreTaskObject.setTaskId(cadreTask.getId());
            cadreTaskObject.setObjectId(item.getDistrictId());
            cadreTaskObject.setObjectType(item.getDistrictType());
            cadreTaskObject.setStatus("0");
            cadreTaskObject.setObjectName(item.getDistrictName());
            cadreTaskObject.setTaskName(cadreTask.getName());
            cadreTaskObject.setTownName(item.getParent().getDistrictName());
            cadreTaskObjectService.save(cadreTaskObject);
            messageCenterService.villageCadresSave(cadreTask.getId(), item.getDistrictId(), type, "[村书记任务]您有一条村书记任务待执行！");
        }
        return cadreTask;
    }

    @Override
    public CadreTask getCurrentBaseInfoTask() {
        return getCurrentTaskByType(BASE_INFO_TASK);
    }

    @Override
    public CadreTask getCurrentReviewTask() {
        return getCurrentTaskByType(REVIEW_TASK);
    }

    @Override
    public CadreTask getCurrentLevelJudgeTask() {
        return getCurrentTaskByType(Level_JUDGE_TASK);
    }

    private CadreTask getCurrentTaskByType(String type) {
        return cadreTaskRepository.findByTypeAndEndTimeGreaterThanEqualOrderByEndTimeDesc(type, LocalDate.now());
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull String id) {
        super.deleteById(id);
        cadreTaskObjectService.deleteByTaskId(id);
    }

}