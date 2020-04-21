package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.InformationAuditSearchable;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskDTO;
import com.cloudkeeper.leasing.identity.dto.cadretask.PromotionCadresDTO;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictSearchable;
import com.cloudkeeper.leasing.identity.repository.CadreTaskRepository;
import com.cloudkeeper.leasing.identity.repository.VillageCadresRepository;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;
import com.cloudkeeper.leasing.identity.vo.CadreTaskVO;
import com.cloudkeeper.leasing.identity.vo.VillageCadresInfoVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 村书记模块任务 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadreTaskServiceImpl extends BaseServiceImpl<CadreTask> implements CadreTaskService {

    private static final String BASE_INFO_TASK = "基本信息更新";

    private static final String GANBU_INFO_TASK = "村干部信息更新";

    private static final String REVIEW_TASK = "考核实施";

    private static final String MAKE_REVIEW_API_CONTENT = "考核指标内容制定";

    private static final String LEVEL_JUDGE_TASK = "职级评定";

    private static final String DAILY_REVIEW = "日常工作考核";

    /** 村书记模块任务 repository */
    private final CadreTaskRepository cadreTaskRepository;

    private final SysDistrictService sysDistrictService;

    private final CadreTaskObjectService cadreTaskObjectService;

    private final MessageCenterService messageCenterService;

    private final VillageCadresRepository villageCadresRepository;

    private final InformationAuditService informationAuditService;

    private final DetectionIndexService detectionIndexService;

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
                .withMatcher("attach", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("taskModule", ExampleMatcher.GenericPropertyMatchers.startsWith());
    }

    @Override
    @Transactional
    public CadreTask save(CadreTaskDTO cadreTaskDTO) {
        CadreTask cadreTask = super.save(cadreTaskDTO.convert(CadreTask.class));
        String type = cadreTaskDTO.getType();
        String msg = "[村书记模块]您有一条村书记" + type + "任务待执行！";
        List<SysDistrict> all = new ArrayList<>();

        SysDistrictSearchable sysDistrictSearchable = new SysDistrictSearchable();
        if (BASE_INFO_TASK.equals(type) || MAKE_REVIEW_API_CONTENT.equals(type) || DAILY_REVIEW.equals(type)) {
            sysDistrictSearchable.setDistrictType("Party");
            sysDistrictSearchable.setDistrictLevel(2);
            all = sysDistrictService.findAll(sysDistrictSearchable);
        } else if (REVIEW_TASK.equals(type)) {
            List<SysDistrict> party = sysDistrictService.findAllByDistrictLevelAndDistrictType(3, "Party");
            for (SysDistrict sysDistrict: party) {
                DetectionIndex detectionIndex = new DetectionIndex();
                detectionIndex.setDistrictId(sysDistrict.getDistrictId());
                detectionIndex.setDistrictName(sysDistrict.getDistrictName());
                detectionIndex.setTaskId(cadreTask.getId());
                detectionIndex.setTaskName(cadreTask.getName());
                detectionIndexService.save(detectionIndex);
            }
            sysDistrictSearchable.setDistrictType("Depart");
            all = sysDistrictService.findAll(sysDistrictSearchable);
            all.addAll(sysDistrictService.findAllByDistrictLevelAndDistrictType(2, "Party"));
            all.addAll(sysDistrictService.findAllByDistrictLevelAndDistrictType(1, "Party"));
        }else if (LEVEL_JUDGE_TASK.equals(type)) {
            List<PromotionCadresDTO> promotionCadres = cadreTaskDTO.getPromotionCadres();
            Set<String> set = promotionCadres.stream().map(PromotionCadresDTO::getTownId).collect(Collectors.toSet());
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysDistrict.class);
            if (set.size() == 0) {
                return null;
            }
            detachedCriteria.add(Restrictions.in("districtId", set));
            all = sysDistrictService.findAll(detachedCriteria);
        } else if (GANBU_INFO_TASK.equals(type)){
            all = sysDistrictService.findAllByDistrictLevelAndDistrictType(3, "Party");
        } else {
            return null;
        }
        for (SysDistrict item : all) {
            CadreTaskObject cadreTaskObject = new CadreTaskObject();
            cadreTaskObject.setTaskId(cadreTask.getId());
            cadreTaskObject.setObjectId(item.getDistrictId());
            cadreTaskObject.setObjectType(item.getDistrictType());
            cadreTaskObject.setStatus("0");
            cadreTaskObject.setObjectName(item.getDistrictName());
            cadreTaskObject.setTaskName(cadreTask.getName());
            cadreTaskObject.setTownName(item.getOrgParentName());
            cadreTaskObjectService.save(cadreTaskObject);
            messageCenterService.villageCadresSave(cadreTask.getId(), item.getDistrictId(), type, msg);
        }
        return cadreTask;
    }

    @Override
    public CadreTask getCurrentBaseInfoTask() {
        return getCurrentTaskByType(BASE_INFO_TASK);
    }

    @Override
    public CadreTask getSecretaryTask() {
        return getCurrentTaskByType(GANBU_INFO_TASK);
    }

    @Override
    public CadreTask getCurrentReviewTask() {
        return getCurrentTaskByType(REVIEW_TASK);
    }

    @Override
    public CadreTask getCurrentLevelJudgeTask() {
        return getCurrentTaskByType(LEVEL_JUDGE_TASK);
    }

    @Override
    public CadreTask getCurrentTaskByType(String type) {
        return cadreTaskRepository.findByTypeAndEndTimeGreaterThanEqualOrderByEndTimeDesc(type, LocalDate.now());
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull String id) {
        super.deleteById(id);
        cadreTaskObjectService.deleteByTaskId(id);
    }

    @Override
    public List<CadreTaskObjectVO> getDetailByTaskId(String taskId) {
        Optional<CadreTask> optionalById = findOptionalById(taskId);
        if (!optionalById.isPresent()) {
            return null;
        }
        List<CadreTaskObject> allByTaskId = cadreTaskObjectService.findAllByTaskId(taskId);
        List<CadreTaskObjectVO> convert = CadreTaskObject.convert(allByTaskId, CadreTaskObjectVO.class);
        for (CadreTaskObjectVO item : convert) {
            String sql = "select TOP 1 a.villageId, vc.name, vc.parentDistrictId, vc.districtName, vc.districtId, a.status as state ,a.modifiedAt from ( SELECT * from Information_Audit ia  " +
                    "WHERE ia.taskId = '" + taskId + "' ) a left join village_cadres vc on vc.id = a.villageId WHERE vc.parentDistrictId = '" + item.getObjectId() +"' GROUP BY a.villageId, vc.name, vc.parentDistrictId, vc.districtName, vc.districtId, a.status , a.modifiedAt ORDER BY a.modifiedAt DESC";
            List<VillageCadresInfoVO> villageCadresInfoVOS = findAllBySql(VillageCadresInfoVO.class, sql);
            item.setVillageCadres(villageCadresInfoVOS);
        }
        return convert;
    }

    @Override
    public Map<String, List> activitiesCompletion(String year, String objectType,String taskType) {
        String sql = "SELECT b.id,b.taskId as taskId,b.taskName as taskName,b.status as status,b.objectId as objectId,b.objectName as objectName,b.currentPercent as currentPercent FROM cadre_task a JOIN cadres_task_object b ON a.id = b.taskId and year(a.createdAt) = '"+ year;
        String nameSql = "SELECT name FROM cadre_task where year(createdAt) = '"+ year +"'";
        if (taskType != null) {
            sql += "' and a.type = '"+ taskType +"'";
            nameSql += "' and type = '"+ taskType +"' ORDER BY createdAt";
        }
        sql += "' and b.objectType = '" + objectType + "' ORDER BY b.objectId, a.createdAt";
        List<CadreTaskObjectVO> allBySql = super.findAllBySql(CadreTaskObjectVO.class, sql);
        List<CadreTaskVO> cadreTaskVOS = super.findAllBySql(CadreTaskVO.class, nameSql);
        Map<String,List> map  = new LinkedHashMap<>();
        allBySql.forEach(item -> {
            String dId = item.getObjectId();
            //补O 排序
            String key = dId + String.format("%1$0"+(12-dId.length())+"d",0) + "," + item.getObjectName() + "," + "2";
            if (map.containsKey(key)){
                map.get(key).add(item);
            }else{
                List list = new ArrayList();
                list.add(item);
                map.put(key,list);
            }
        });
        List<String> strs = new ArrayList<>();
        for (CadreTaskVO c : cadreTaskVOS){
            strs.add(c.getName());
        }
        map.put("title",strs);
        return map;
    }


}
