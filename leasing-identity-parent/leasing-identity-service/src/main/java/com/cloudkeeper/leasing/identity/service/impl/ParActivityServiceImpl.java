package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.distlearningactivityvideo.DistLearningActivityVideoSearchable;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivityDTO;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivitySearchable;
import com.cloudkeeper.leasing.identity.dto.paractivityreleasefile.ParActivityReleaseFileSearchable;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictSearchable;
import com.cloudkeeper.leasing.identity.enumeration.TaskTypeEnum;
import com.cloudkeeper.leasing.identity.repository.ParActivityRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityObjectService;
import com.cloudkeeper.leasing.identity.service.ParActivityService;
import com.cloudkeeper.leasing.identity.service.SysUserService;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import com.cloudkeeper.leasing.identity.vo.TVIndexVO;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 活动 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityServiceImpl extends BaseServiceImpl<ParActivity> implements ParActivityService {

    /** 活动 repository */
    private final ParActivityRepository parActivityRepository;

    private final ParActivityReleaseFileServiceImpl parActivityReleaseFileServiceImpl;
    /** 组织 */
    private final SysDistrictServiceImpl sysDistrictServiceImpl;

    /** 组织 */
    private final SysUserService sysUserService;

    @Override
    protected BaseRepository<ParActivity> getBaseRepository() {
        return parActivityRepository;
    }

    private  final DistLearningActivityVideoServiceImpl distLearningActivityVideoService;

    private final ParActivityObjectService parActivityObjectService;
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
                .withMatcher("alarmTime", ExampleMatcher.GenericPropertyMatchers.contains());
    }
    //重写save方法
    @Nonnull
    @Override
    public ParActivityVO save(@Nonnull ParActivityDTO parActivityDTO) {
        ParActivity p = parActivityDTO.convert(ParActivity.class);
        ParActivity parActivity = super.save(p);

        handleReleaseFiles(parActivity.getId(), parActivityDTO.getFileUrls());
        if (TaskTypeEnum.DistLearning.toString().equals(parActivityDTO.getTaskType())) {
            handleVideoFiles(parActivity.getId(),parActivityDTO.getVideo());
        }
        handleObjIds(parActivity.getId(), parActivityDTO.getTaskObject());
        return parActivity.convert(ParActivityVO.class);
    }

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
                distLearningActivityVideo.setVideoCover(item.getVideoCover());
                distLearningActivityVideo.setVideoUrl(item.getVideoUrl());
                results.add(distLearningActivityVideoService.save(distLearningActivityVideo));
            });
        }
        return results;
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
    private void handleObjIds(String activityId, TaskObject taskObject) {
        parActivityObjectService.deleteAllByActivityId(activityId);
        if(taskObject.getSid().size()!=0){
            SysDistrictSearchable sysDistrictSearchable = new SysDistrictSearchable();
            sysDistrictSearchable.setDistrictLevel(3);
           List<SysDistrict> sysDistricts = sysDistrictServiceImpl.findAll(sysDistrictSearchable);
            for(int i=0;i<sysDistricts.size();i++){
                ParActivityObject parActivityObject = new ParActivityObject();
                parActivityObject.setActivityId(activityId);
                parActivityObject.setOrganizationId(sysDistricts.get(i).getDistrictId());
                parActivityObject.setStatus("0");
                parActivityObject.setAttachTo(sysDistricts.get(i).getAttachTo());
                parActivityObjectService.save(parActivityObject);
            }
        }else {
                if(taskObject.getZid().size()!=0){
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
                    //村级去除选中镇级下的重复村
                    for(int i=0;i<cids.size();i++){
                        for(int j=0;j<zcidsList.size();j++){
                            if(cids.get(i) == zcidsList.get(j)){
                                cids.remove(i);
                            }
                        }
                    }
                    //新增镇下面的村
                    for(int i=0;i<taskObject.getZid().size();i++){
                        Set<SysDistrict> set =  sysDistrictServiceImpl.sysDistrictsByAttachTo(taskObject.getZid().get(i));
                        for (SysDistrict sysDistrict: set) {
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
                        ParActivityObject parActivityObject = new ParActivityObject();
                        parActivityObject.setActivityId(activityId);
                        parActivityObject.setOrganizationId(cids.get(i));
                        parActivityObject.setAttachTo(sysDistrictServiceImpl.sysDistrictsByDistrictId(cids.get(i)).get(0).getAttachTo());
                        parActivityObject.setStatus("0");
                        parActivityObjectService.save(parActivityObject);
                    }
                    }else{
                    ////选中的所有村级
                    List<String> cidss = taskObject.getCid();
                    //新增上级镇没有选的村
                    for(int j=0;j<cidss.size();j++){
                        ParActivityObject parActivityObject = new ParActivityObject();
                        parActivityObject.setActivityId(activityId);
                        parActivityObject.setOrganizationId(cidss.get(j));
                        parActivityObject.setAttachTo(sysDistrictServiceImpl.sysDistrictsByDistrictId(cidss.get(j)).get(0).getAttachTo());
                        parActivityObject.setStatus("0");
                        parActivityObjectService.save(parActivityObject);
                    }
                }

        }

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
            detachedCriteria.add(Restrictions.in("id", activityIdsByDistrictCode));
        } else if (roleCode.equals("COUNTRY_SIDE_ACTOR")) {
            List<String> activityIdsByDistrictCode = parActivityObjectService.findActivityIdsByOrganizationId(districtCode);
            detachedCriteria.add(Restrictions.in("id", activityIdsByDistrictCode));
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
        if ("ACTIVE".equals(parActivitySearchable.getCurrentStatus())) {
            detachedCriteria.add(Restrictions.le("month", lastDay()));
        } else if ("PLAN".equals(parActivitySearchable.getCurrentStatus())) {
            detachedCriteria.add(Restrictions.ge("month", lastDay()));
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
}
