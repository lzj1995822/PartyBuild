package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.distlearningactivityvideo.DistLearningActivityVideoSearchable;
import com.cloudkeeper.leasing.identity.dto.feedbacktemplateitem.FeedbackTemplateItemDTO;
import com.cloudkeeper.leasing.identity.dto.feedbacktemplateitem.FeedbackTemplateItemSearchable;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivityDTO;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivitySearchable;
import com.cloudkeeper.leasing.identity.dto.paractivityreleasefile.ParActivityReleaseFileSearchable;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictSearchable;
import com.cloudkeeper.leasing.identity.enumeration.TaskTypeEnum;
import com.cloudkeeper.leasing.identity.repository.ParActivityRepository;
import com.cloudkeeper.leasing.identity.repository.SysDistrictRepository;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.*;
import io.netty.util.internal.IntegerHolder;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 活动 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityServiceImpl extends BaseServiceImpl<ParActivity> implements ParActivityService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 活动 repository */
    private final ParActivityRepository parActivityRepository;

    private final ParActivityReleaseFileServiceImpl parActivityReleaseFileServiceImpl;
    /** 组织 */
    private final SysDistrictServiceImpl sysDistrictServiceImpl;

    private  final SysDistrictRepository sysDistrictRepository;

    /** 组织 */
    private final SysUserService sysUserService;

    private final TVSignInService tvSignInService;

    @Override
    protected BaseRepository<ParActivity> getBaseRepository() {
        return parActivityRepository;
    }

    private  final DistLearningActivityVideoServiceImpl distLearningActivityVideoService;

    private final ParActivityObjectService parActivityObjectService;

    private final FeedbackItemValueService feedbackItemValueService;

    private final FeedbackTemplateItemService feedbackTemplateItemService;

    private final ActivityOfficeProgressService activityOfficeProgressService;

    // 农村 objectType 1
    private static final String COUNTRY_SIDE_OBJECT_TYPE = "1";

    // 市直机关工委以及所有党组织加各局党委以及所属所有党组织 objectType 2-1
    private static final String OFFICE_ALL_OBJECT_TYPE = "2-1";

    // 市直机关工委以及所有所属党组织 objectType 2-2
    private static final String OFFICE_ONLY_OBJECT_TYPE = "2-2";

    // 市直机关工委以及所有所属党组织加上各局委所属机关党支部 objectType 2-3
    private static final String OFFICE_ONLY_PART_OBJECT_TYPE = "2-3";




    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("month", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("context", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtID", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("taskType", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("releaseTime", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("objectType", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("alarmTime", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    //重写save方法
    @Nonnull
    @Override
    @Transactional
    public ParActivityVO save(@Nonnull ParActivityDTO parActivityDTO) {
        ParActivity parActivity;
        if (!StringUtils.isEmpty(parActivityDTO.getId())){
            Optional<ParActivity> optionalById = findOptionalById(parActivityDTO.getId());
            if (!optionalById.isPresent()) {
                return null;
            }
            parActivity = optionalById.get();
            BeanUtils.copyProperties(parActivityDTO, parActivity);

        } else {
            parActivity = parActivityDTO.convert(ParActivity.class);
        }
        parActivity = super.save(parActivity);
        // 处理发布的附件
        handleReleaseFiles(parActivity.getId(), parActivityDTO.getFileUrls());
        ParActivityVO par = parActivity.convert(ParActivityVO.class);
        // 判断是否是新增任务
        if (StringUtils.isEmpty(parActivityDTO.getId())) {
            // 如果是新增任务指派相关任务对象
            par.setBackList(handleNewObject(parActivity.getId(),parActivityDTO.getTemplateId(), parActivityDTO.getObjectType()));
        }
        //par.setBackList(handleObjIds(parActivity.getId(), parActivityDTO.getTaskObject()));
        if (TaskTypeEnum.DistLearning.toString().equals(parActivityDTO.getTaskType())) {
            List<DistLearningActivityVideo> distLearningActivityVideos = handleVideoFiles(parActivity.getId(), parActivityDTO.getVideo());
            if (StringUtils.isEmpty(parActivityDTO.getId())) {
                // 新增需要处理远教签到记录
                handleRecord(parActivity.getId(),distLearningActivityVideos,par.getBackList());
            }
        }
        return par;
    }

    /**
     * 处理任务关联的附件
     * @param activityId 任务id
     * @param fileUrls 文件地址集合
     * @return 附件对象集合
     */
    private List<ParActivityReleaseFile> handleReleaseFiles(String activityId, List<String> fileUrls) {
        ParActivityReleaseFileSearchable parActivityReleaseFileSearchable = new ParActivityReleaseFileSearchable();
        parActivityReleaseFileSearchable.setActivityID(activityId);
        List<ParActivityReleaseFile> all = parActivityReleaseFileServiceImpl.findAll(parActivityReleaseFileSearchable);
        all.stream().forEach(item -> {
            parActivityReleaseFileServiceImpl.deleteById(item.getId());
        });
        ArrayList<ParActivityReleaseFile> results = new ArrayList<>();
        if(!StringUtils.isEmpty(fileUrls)) {
            fileUrls.stream().forEach(item -> {
                ParActivityReleaseFile parActivityReleaseFile = new ParActivityReleaseFile();
                parActivityReleaseFile.setActivityID(activityId);
                parActivityReleaseFile.setUrl(item);
                results.add(parActivityReleaseFileServiceImpl.save(parActivityReleaseFile));
            });
        }
        return results;
    }

    /**
     * 远教任务关联对应视频
     * @param activityId 活动id
     * @param video 视频集合
     * @return 关联的视频集合对象
     */
    private List<DistLearningActivityVideo> handleVideoFiles(String activityId, List<DistLearningActivityVideo> video) {
        DistLearningActivityVideoSearchable distLearningActivityVideoSearchable = new DistLearningActivityVideoSearchable();
        distLearningActivityVideoSearchable.setActivityId(activityId);
        List<DistLearningActivityVideo> all = distLearningActivityVideoService.findAll(distLearningActivityVideoSearchable);
        all.stream().forEach(item -> {
            distLearningActivityVideoService.deleteById(item.getId());
        });

        List<DistLearningActivityVideo> results = new ArrayList<>();
        if(!StringUtils.isEmpty(video)) {
            video.stream().forEach(item -> {
                    DistLearningActivityVideo distLearningActivityVideo = new DistLearningActivityVideo();
                    distLearningActivityVideo.setActivityId(activityId);
                    distLearningActivityVideo.setName(item.getName());
                    distLearningActivityVideo.setLengthOfTime(item.getLengthOfTime());
                    distLearningActivityVideo.setVideoCover("http://172.16.0.152:8082/cms/res/appPoster/stb/" + item.getVideoCover());
                    if (item.getVideoUrl().split("\\=").length > 1) {
                        String mp4 = item.getVideoUrl().split("\\=")[1];
                        distLearningActivityVideo.setVideoUrl("http://172.16.1.140:9391/vod/" + mp4 + ".mp4");
                    }
                results.add(distLearningActivityVideoService.save(distLearningActivityVideo));
            });
        }
        return results;
    }
    //生成远教签到记录
    private void handleRecord(String activityId,List<DistLearningActivityVideo> video,List<String> districtIdList){
        for(int i= 0;i<districtIdList.size();i++){
            for(int j=0;j<video.size();j++){
                TVSignIn tvSignIn = new TVSignIn();
                tvSignIn.setActivityId(activityId);
                tvSignIn.setOrganizationId(districtIdList.get(i));
                tvSignIn.setVideoId(video.get(j).getId());
                tvSignIn.setFlag(0);
                tvSignInService.save(tvSignIn);
            }
        }
    }


    public void deleteAll(String id){
        parActivityRepository.deletePar(id);
        parActivityRepository.deleteParFile(id);
        parActivityRepository.deleteVideo(id);
    }

    @Override
    public ParActivityVO updateAlarmTime(String id, LocalDateTime localDateTime) {
        ParActivity one = parActivityRepository.getOne(id);
        if (StringUtils.isEmpty(one)){
           return  null;
        }
        one.setAlarmTime(localDateTime);
        return  parActivityRepository.save(one).convert(ParActivityVO.class);
    }

    /**
     * 处理被指派的对象
     * @param activityId
     * @param taskObject
     */
    private List<String> handleObjIds(String activityId, TaskObject taskObject) {
        parActivityObjectService.deleteAllByActivityId(activityId);
        if(taskObject.getSid().size()!=0){
            SysDistrictSearchable sysDistrictSearchable = new SysDistrictSearchable();
            sysDistrictSearchable.setDistrictLevel(3);
           List<SysDistrict> sysDistricts = sysDistrictServiceImpl.findAll(sysDistrictSearchable);
            //返回数据
           List<String> backList = new ArrayList<>();
            for(int i=0;i<sysDistricts.size();i++){
                backList.add(sysDistricts.get(i).getDistrictId());
                ParActivityObject parActivityObject = new ParActivityObject();
                parActivityObject.setActivityId(activityId);
                parActivityObject.setOrganizationId(sysDistricts.get(i).getDistrictId());
                parActivityObject.setStatus("0");
                parActivityObject.setAttachTo(sysDistricts.get(i).getAttachTo());
                parActivityObjectService.save(parActivityObject);
            }
            return backList;
        }else {
                if(taskObject.getZid().size()!=0){
                    //返回数据
                    List<String> backList = new ArrayList<>();
                    ////选中的所有村级
                    List<String> cids = taskObject.getCid();
                    //选中的镇级下的所有村级
                    Set<String> zcids = new HashSet<>();
                    //遍历选中镇，并给zcids赋值
                    for(int i=0;i<taskObject.getZid().size();i++){
                        Set<SysDistrict> set =  sysDistrictServiceImpl.sysDistrictsByAttachTo(taskObject.getZid().get(i));
                        for (SysDistrict sysDistrict: set) {
                            zcids.add(sysDistrict.getDistrictId());
                        }
                }
                    //zcids转List
                    List<String> zcidsList = new ArrayList<>(zcids);
                    Collection notExists = new ArrayList<String>(zcidsList);
                    //村级去除选中镇级下的重复村
//                    for(int i=0;i<cids.size();i++){
//                        for(int j=0;j<zcidsList.size();j++){
//                            if(cids.get(i).equals(zcidsList.get(j))){
//                                cids.remove(i);
//                            }
//                        }
//                    }
                    Collection exists=new ArrayList<String>(cids);
                    cids.clear();
                    exists.removeAll(notExists);
                    exists.forEach(ele -> cids.add(ele.toString()));
                    //新增镇下面的村
                    for(int i=0;i<taskObject.getZid().size();i++){
                        Set<SysDistrict> set =  sysDistrictServiceImpl.sysDistrictsByAttachTo(taskObject.getZid().get(i));
                        for (SysDistrict sysDistrict: set) {
                            backList.add(sysDistrict.getDistrictId());
                            ParActivityObject parActivityObject = new ParActivityObject();
                            parActivityObject.setActivityId(activityId);
                            parActivityObject.setOrganizationId(sysDistrict.getDistrictId());
                            parActivityObject.setAttachTo(sysDistrictServiceImpl.sysDistrictsByDistrictId(sysDistrict.getDistrictId()).get(0).getAttachTo());
                            parActivityObject.setStatus("0");
                            parActivityObjectService.save(parActivityObject);
                        }

                        }
                    //新增上级镇没有选的村
                    for(int i=0;i<cids.size();i++){
                        backList.add(cids.get(i));
                        ParActivityObject parActivityObject = new ParActivityObject();
                        parActivityObject.setActivityId(activityId);
                        parActivityObject.setOrganizationId(cids.get(i));
                        parActivityObject.setAttachTo(sysDistrictServiceImpl.sysDistrictsByDistrictId(cids.get(i)).get(0).getAttachTo());
                        parActivityObject.setStatus("0");
                        parActivityObjectService.save(parActivityObject);
                    }
                    return backList;
                    }else{
                    //返回数据
                    List<String> backList = new ArrayList<>();
                    ////选中的所有村级
                    List<String> cidss = taskObject.getCid();
                    //新增上级镇没有选的村
                    for(int j=0;j<cidss.size();j++){
                        backList.add(cidss.get(j));
                        ParActivityObject parActivityObject = new ParActivityObject();
                        parActivityObject.setActivityId(activityId);
                        parActivityObject.setOrganizationId(cidss.get(j));
                        parActivityObject.setAttachTo(sysDistrictServiceImpl.sysDistrictsByDistrictId(cidss.get(j)).get(0).getAttachTo());
                        parActivityObject.setStatus("0");
                        parActivityObjectService.save(parActivityObject);
                    }
                    return backList;
                }

        }

    }

    /**
     * 处理新被指派的对象(根据机关或者农村)
     * @param activityId
     * @param objectType 任务对象类型
     * @return
     */
    private List<String> handleNewObject(@Nonnull String activityId, String templateId, String objectType) {
        List<SysDistrict> sysDistrictsAL;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysDistrict.class);
        switch (objectType) {
            case COUNTRY_SIDE_OBJECT_TYPE:
                detachedCriteria.add(Restrictions.in("districtType", "Party"));
                detachedCriteria.add(Restrictions.eq("districtLevel", 3));
                break;
            case OFFICE_ALL_OBJECT_TYPE:
                // 发布给所有的机关党组织 市直机关工委过滤掉 (除市直机关工委所有机关类型党组织)
                detachedCriteria.add(Restrictions.in("districtType", "Office"));
                detachedCriteria.add(Restrictions.ne("districtId", "0118"));
                break;
            case OFFICE_ONLY_OBJECT_TYPE:
                // 发布给市直机关工委所属的党组织
                detachedCriteria.add(Restrictions.in("districtType", "Office"));
                detachedCriteria.add(Restrictions.ne("districtId", "0118"));
                detachedCriteria.add(Restrictions.like("districtId", "0118", MatchMode.START));
                break;
            case OFFICE_ONLY_PART_OBJECT_TYPE:
                // 发布给市直机关工委所属的党组织及各局委所属机关党支部
                detachedCriteria.add(Restrictions.in("districtType", "Office"));
                detachedCriteria.add(Restrictions.ne("districtId", "0118"));
                detachedCriteria.add(Restrictions.or(Restrictions.like("districtId", "0118", MatchMode.START), Restrictions.eq("isOfficeBranch", "1")));
                break;
            default:
                return null;
        }
        sysDistrictsAL = sysDistrictServiceImpl.findAll(detachedCriteria);
        return generateActicityObject(objectType, activityId, templateId, sysDistrictsAL);
    }

    // 生成指派信息
    private List<String> generateActicityObject(String objectType, String activityId, String templateId, List<SysDistrict> sysDistrictsAL) {
        //查询任务下所需材料配置项
        List<FeedbackTemplateItem> items = new ArrayList<>();
        if (!StringUtils.isEmpty(templateId)) {
            FeedbackTemplateItemSearchable feedbackTemplateItemSearchable = new FeedbackTemplateItemSearchable();
            feedbackTemplateItemSearchable.setTemplateId(templateId);
            items = feedbackTemplateItemService.findAll(feedbackTemplateItemSearchable);
        }

        // 指派任务
        List<String> backList = new ArrayList<>();
        for(int i=0;i<sysDistrictsAL.size();i++){
            backList.add(sysDistrictsAL.get(i).getDistrictId());
            ParActivityObject parActivityObject = new ParActivityObject();
            parActivityObject.setOrganizationId(sysDistrictsAL.get(i).getDistrictId());
            parActivityObject.setIsWorking("0");
            parActivityObject.setActivityId(activityId);
            parActivityObject.setStatus("0");
            parActivityObject.setAttachTo(sysDistrictsAL.get(i).getAttachTo());
            parActivityObject.setObjectType(objectType);
            parActivityObject = parActivityObjectService.save(parActivityObject);
            for (FeedbackTemplateItem item : items) {
                FeedbackItemValue feedbackItemValue = new FeedbackItemValue();
                feedbackItemValue.setItemId(item.getId());
                feedbackItemValue.setType(item.getType());
                feedbackItemValue.setCode(item.getCode());
                feedbackItemValue.setName(item.getName());
                feedbackItemValue.setObjectId(parActivityObject.getId());
                feedbackItemValueService.save(feedbackItemValue);
            };
            Optional<ParActivity> optionalById = findOptionalById(activityId);
            ParActivity parActivity = optionalById.get();
            parActivity.setTemplateItem(items.stream().map(FeedbackTemplateItem::getName).collect(Collectors.joining("、")));
            super.save(parActivity);
        }
        return backList;
    }
    //更新进度信息
    private  List<PassPercentVO> getFinishRatio(String activityId) {
        // 新结构统计sql
        String sql = "SELECT " +
                " attachTo as townCode, " +
                " waitCheck, " +
                " passed, " +
                " fail, " +
                " ROUND( cast( passed AS FLOAT ) / ( passed + fail + waitCheck ), 3 ) AS finishRatio  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " SD.attachTo, " +
                " COUNT( CASE WHEN PO.STATUS = 1 THEN 1 ELSE NULL END ) waitCheck, " +
                " COUNT( CASE WHEN PO.STATUS = 2 THEN 1 ELSE NULL END ) passed, " +
                " COUNT( CASE WHEN PO.STATUS = 3 OR PO.STATUS = 0 THEN 1 ELSE NULL END ) fail  " +
                "FROM " +
                " PAR_ActivityObject PO " +
                " LEFT JOIN SYS_District SD ON PO.organizationId = SD.districtId  " +
                "WHERE " +
                " PO.activityId = '" + activityId + "'  " +
                " AND SD.isDelete = 0 " +
                "GROUP BY " +
                " SD.attachTo  " +
                " ) a";
        return  super.findAllBySql(PassPercentVO.class, sql);
    }

    private PassPercentVO getTotalPercent(String activityId) {
        String sql = "SELECT " +
                " waitCheck, " +
                " passed, " +
                " fail, " +
                " ROUND( cast( passed AS FLOAT ) / ( passed + fail + waitCheck ), 3 ) AS finishRatio  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " COUNT( CASE WHEN PO.STATUS = 1 THEN 1 ELSE NULL END ) waitCheck, " +
                " COUNT( CASE WHEN PO.STATUS = 2 THEN 1 ELSE NULL END ) passed, " +
                " COUNT( CASE WHEN PO.STATUS = 3 OR PO.STATUS = 0 THEN 1 ELSE NULL END ) fail  " +
                "FROM " +
                " PAR_ActivityObject PO LEFT join SYS_District SD on PO.organizationId = SD.districtId " +
                "WHERE " +
                " PO.activityId = '" + activityId + "'  " +
                " AND SD.isDelete = 0 " +
                " ) a";
        return  super.findBySql(PassPercentVO.class, sql);
    }

    // 更新进度
    public ParActivity updateProgress(String activityId) {
        Optional<ParActivity> byId = parActivityRepository.findById(activityId);
        if (!byId.isPresent()) {
            return null;
        }
        ParActivity parActivity = byId.get();

        List<PassPercentVO> finishRatio = this.getFinishRatio(activityId);
        //机关任务
        if (parActivity.getObjectType().equals("2")) {
            List<ActivityOfficeProgress> activityOfficeProgresses = parActivity.getActivityOfficeProgresses();
            Map<String, ActivityOfficeProgress> collect = activityOfficeProgresses.stream().collect(Collectors.toMap(ActivityOfficeProgress::getDistrictId, activityOfficeProgresse -> activityOfficeProgresse));
            for (PassPercentVO item : finishRatio) {
                ActivityOfficeProgress activityOfficeProgress = collect.get(item.getTownCode());
                if (activityOfficeProgress != null) {
                    activityOfficeProgress.setPercent(item.getFinishRatio());
                } else {
                    activityOfficeProgress = new ActivityOfficeProgress();
                    activityOfficeProgress.setPercent(item.getFinishRatio());
                    activityOfficeProgress.setActivityId(parActivity.getId());
                    activityOfficeProgress.setDistrictId(item.getTownCode());
                }
                this.activityOfficeProgressService.save(activityOfficeProgress);
            }
        } else if (parActivity.getObjectType().equals("1")) {
            for (PassPercentVO item : finishRatio) {
                BigDecimal value =  item.getFinishRatio();
                switch (item.getTownCode()) {
                    case "0104":
                        parActivity.setBaiTuPercent(value);
                        break;
                    case "0103":
                        parActivity.setGuoZhuangPercent(value);
                        break;
                    case "0107":
                        parActivity.setBaoHuaPercent(value);
                        break;
                    case "0106":
                        parActivity.setBianChengPercent(value);
                        break;
                    case "0102":
                        parActivity.setHouBaiPercent(value);
                        break;
                    case "0111":
                        parActivity.setKaiFaPercent(value);
                        break;
                    case "0112":
                        parActivity.setMaoShanFengJingPercent(value);
                        break;
                    case "0105":
                        parActivity.setMaoShanPercent(value);
                        break;
                    case "0108":
                        parActivity.setTianWangPercent(value);
                        break;
                    case "0109":
                        parActivity.setHuaYangPercent(value);
                        break;
                    case  "0101":
                        parActivity.setXiaShuPercent(value);
                        break;
                }
            }
        } else {
            logger.warn("活动任务对象异常");
        }
        BigDecimal totalPercent = this.getTotalPercent(activityId).getFinishRatio();
        parActivity.setTotalPercent(totalPercent);
        return this.save(parActivity);
    }

    @Override
    public Page<ParActivity> handleDifferentRole(ParActivitySearchable parActivitySearchable, Pageable pageable) {
        Optional<SysUser> optionalById = sysUserService.findOptionalById(getCurrentPrincipalId());
        if (!optionalById.isPresent()) {
            return null;
        }
        SysUser sysUser = optionalById.get();
        String districtCode = sysUser.getSysDistrict().getDistrictId();
        String roleCode = sysUser.getRole().getCode();
        DetachedCriteria detachedCriteria = getDetachedCriteria(parActivitySearchable);
         if (roleCode.equals("TOWN_REVIEWER")) {
            List<String> activityIdsByDistrictCode = parActivityObjectService.findActivityIdsByDistrictCode(districtCode);
            if (activityIdsByDistrictCode.size() > 0) {
                detachedCriteria.add(Restrictions.in("id", activityIdsByDistrictCode));
            }
        } else if (roleCode.equals("COUNTRY_SIDE_ACTOR")) {
            List<String> activityIdsByDistrictCode = parActivityObjectService.findActivityIdsByOrganizationId(districtCode);
             if (activityIdsByDistrictCode.size() > 0) {
                 detachedCriteria.add(Restrictions.in("id", activityIdsByDistrictCode));
             }
        }
         //不加其他查询条件默认为市级
        int resultCount = getTotalCount(detachedCriteria);
        detachedCriteria.addOrder(Order.desc("month"));
        return super.findAll(detachedCriteria, pageable,resultCount);
    }

    @Override
    public Result<TVIndexVO> tvIndex() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ParActivity.class);
        detachedCriteria.add(Restrictions.between("month", firstDay(), lastDay()));
        List<ParActivity> currentMonth = findAll(detachedCriteria);
        List<ParActivityVO> currentMonthVO = ParActivity.convert(currentMonth, ParActivityVO.class);

        detachedCriteria = DetachedCriteria.forClass(ParActivity.class);
        detachedCriteria.add(Restrictions.between("month", nextMonthFirstDay(), nextMonthLastDay()));
        List<ParActivity> nextMonth = findAll(detachedCriteria);
        List<ParActivityVO> nextMonthVO = ParActivity.convert(nextMonth, ParActivityVO.class);

        return Result.of(new TVIndexVO(currentMonthVO, nextMonthVO));
    }

    @Override
    public Result<Map<String,List>> activityCompletion(String year, String districtId, String objectType,String districtType) {
        Map<String, List> stringListMap = activitiesCompletion(year, districtId,objectType,districtType);
        stringListMap.put("title",currentActivities(year,objectType));
        return Result.of(stringListMap);
    }

    private DetachedCriteria getDetachedCriteria(ParActivitySearchable parActivitySearchable) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ParActivity.class);
        if (!StringUtils.isEmpty(parActivitySearchable.getDistrictID())) {
            detachedCriteria.add(Restrictions.eq("districtId", parActivitySearchable.getDistrictID()));
        }
        if (!StringUtils.isEmpty(parActivitySearchable.getContext())) {
            detachedCriteria.add(Restrictions.like("context", parActivitySearchable.getContext()));
        }
        if (!StringUtils.isEmpty(parActivitySearchable.getTitle())) {
            detachedCriteria.add(Restrictions.like("title", parActivitySearchable.getTitle()));
        }
        if (!StringUtils.isEmpty(parActivitySearchable.getTaskType())) {
            detachedCriteria.add(Restrictions.eq("taskType", parActivitySearchable.getTaskType()));
        }
        if (!StringUtils.isEmpty(parActivitySearchable.getType())) {
            detachedCriteria.add(Restrictions.eq("type", parActivitySearchable.getType()));
        }
        if (!StringUtils.isEmpty(parActivitySearchable.getObjectType())) {
            detachedCriteria.add(Restrictions.like("objectType", parActivitySearchable.getObjectType(), MatchMode.START));
        }
        if ("ACTIVE".equals(parActivitySearchable.getCurrentStatus())) {
            detachedCriteria.add(Restrictions.le("month", lastDay()));
        } else if ("PLAN".equals(parActivitySearchable.getCurrentStatus())) {
            detachedCriteria.add(Restrictions.gt("month", lastDay()));
        }
        return  detachedCriteria;
    }

    private LocalDate nextMonthLastDay() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, 1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return toLocalDate(ca);
    }

    private LocalDate nextMonthFirstDay() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, 1);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return toLocalDate(ca);
    }

    private LocalDate lastDay() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return toLocalDate(ca);
    }

    private LocalDate firstDay() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return toLocalDate(ca);
    }

    private LocalDate toLocalDate(Calendar calendar) {
        Date date = calendar.getTime();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    private Map<String,List> activitiesCompletion(String year,String districtId,String objectType,String districtType){
        String sql = "SELECT a.id as activityId,a.month,a.title,d.districtId,d.districtLevel, d.id AS organizationId,d.districtName,a.objectType,o.status,o.id AS objectId " +
                "FROM PAR_Activity AS a " +
                "INNER JOIN  SYS_District AS d ON LEN(d.districtId)=6 " +
                "LEFT JOIN PAR_ActivityObject AS o ON a.id = o.activityId AND d.districtId = o.organizationId " +
                "WHERE year(month)="+year+"AND d.districtId like "+"'"+districtId+"%"+"' "+"AND d.isDelete = 0 "+"And a.objectType like '" + objectType +"%' And d.districtType ="+"'"+districtType+"' "+
                "ORDER BY districtId ASC,month Asc , releaseTime Asc";
        if (objectType.contains("2")) {
            sql = "SELECT a.id as activityId,a.month,a.title,d.districtId,d.districtLevel, d.id AS organizationId,d.districtName,a.objectType,o.status,o.id AS objectId " +
                    "FROM PAR_Activity AS a " +
                    "INNER JOIN  SYS_District AS d ON d.districtType = 'Office' AND d.districtId != '0118'" +
                    "LEFT JOIN PAR_ActivityObject AS o ON a.id = o.activityId AND d.districtId = o.organizationId " +
                    "WHERE year(month)="+year+"AND d.districtId like "+"'"+districtId+"%"+"' "+"AND d.isDelete = 0 "+"And a.objectType like '" + objectType +"%' And d.districtType ="+"'"+districtType+"' "+
                    "ORDER BY districtId ASC,month Asc , releaseTime Asc";
        }
        List<ActivitiesCompletionVO> allBySql = super.findAllBySql(ActivitiesCompletionVO.class, sql);
        Map<String,List> map  = new LinkedHashMap<>();
        allBySql.forEach(item -> {
            String dId = item.getDistrictId();
            //补O 排序
            String key = dId + String.format("%1$0"+(10-dId.length())+"d",0) + "," + item.getDistrictName() + "," + item.getDistrictLevel();
                if (map.containsKey(key)){
                    map.get(key).add(item);
                }else{
                    List list = new ArrayList();
                    list.add(item);
                    map.put(key,list);
                }
       });
        return map;
    }

    private List currentActivities(String year,String objectType){
        List list  = new ArrayList();
        String sql = "SELECT * FROM PAR_Activity WHERE year(month) = "+year+ " And objectType like '" + objectType + "%'" +
                " ORDER BY month Asc , releaseTime Asc";
        List<ActivityVO> allBySql = super.findAllBySql(ActivityVO.class, sql);
        for(int i=0;i<allBySql.size();i++){
            list.add(allBySql.get(i).getTitle());
        }
        return list;
    }
    public List<ParActivityAllVO> listAll(){
        String sql = "SELECT * FROM PAR_Activity where isDelete=0 ORDER BY month desc";
        Optional<SysUser> user = sysUserService.findOptionalById(super.getCurrentPrincipalId());
        if (user.isPresent()) {
            SysUser sysUser = user.get();
            if (sysUser.getDistrictId().equals("0118")) {
                sql = "SELECT * FROM PAR_Activity where isDelete=0 and objectType like '2%' ORDER BY month desc";
            }
        }
        List<ParActivityAllVO> allBySql = super.findAllBySql(ParActivityAllVO.class, sql);
        return allBySql;
    }
}
