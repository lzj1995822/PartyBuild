package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.constant.AuthorizationConstants;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.base.utils.TokenUtil;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectDTO;
import com.cloudkeeper.leasing.identity.repository.*;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.ParActivityObjectVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.TownDetailVO;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 任务对象 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityObjectServiceImpl extends BaseServiceImpl<ParActivityObject> implements ParActivityObjectService {

    /** 任务对象 repository */
    @Autowired
    private ParActivityObjectRepository parActivityObjectRepository;

    /**
     * 电视截图
     */
    @Autowired
    private ParPictureInfroRepository parPictureInfroRepository;

    /**
     * 手机截图
     */
    @Autowired
    private ParActivityPictureRepository parActivityPictureRepository;

    /**
     * feedBack
     */
    @Autowired
    private ParActivityFeedbackRepository parActivityFeedbackRepository;

    @Autowired
    private ParActivityRepository parActivityRepository;

    @Autowired
    private ParActivityPerformRepository parActivityPerformRepository;

    @Autowired
    private ParCameraRepository parCameraRepository;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysDistrictService sysDistrictService;

    @Autowired
    private SysDistrictRepository sysDistrictRepository;

    @Autowired
    private SysConfigurationService sysConfigurationService;

    @Autowired
    private ParCameraService parCameraService;

    /** redis 操作 */
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MessageCenterService messageCenterService;

    @Autowired
    private DynamicTaskService dynamicTaskService;
    @Override
    protected BaseRepository<ParActivityObject> getBaseRepository() {
        return parActivityObjectRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("isWorking", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("activityId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("organizationId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("attachTo", ExampleMatcher.GenericPropertyMatchers.exact());
    }

    @Override
    public void deleteAllByActivityId(String activityId) {
        parActivityObjectRepository.deleteAllByActivityId(activityId);
    }

    @Override
    public List<String> findActivityIdsByDistrictCode(String districtCode) {
        return parActivityObjectRepository.findActivityIdsByDistrictCode(districtCode);
    }

    @Override
    public List<String> findActivityIdsByOrganizationId(String organizationId) {
        return parActivityObjectRepository.findActivityIdsByOrganizationId(organizationId);
    }

    @Override
    public BigDecimal handleActivityCompleteRate(String organizationId) {
        Integer sum = parActivityObjectRepository.countAllByOrganizationIdStartingWith(organizationId);
        Integer completeSum = parActivityObjectRepository.countAllByOrganizationIdStartingWithAndStatus(organizationId,"2");
        BigDecimal total = new BigDecimal(sum);
        BigDecimal finished = new BigDecimal(completeSum);
        return finished.divide(total, 3,BigDecimal.ROUND_DOWN);
    }

    @Override
    public ParActivityObject findByOrganizationIdAndActivityId(String organizationId, String activityId) {
        return parActivityObjectRepository.findByOrganizationIdAndActivityId(organizationId, activityId);
    }

    @Override
    @Transactional
    public void initPerActivity() {
        List<ParActivity> parActivities = parActivityRepository.findAll();
        List<SysDistrict> sysDistricts = sysDistrictService.findAllByDistrictLevel(3);
        for (ParActivity parActivity: parActivities) {
            for (SysDistrict sysDistrict: sysDistricts) {
                ParActivityObject parActivityObject = new ParActivityObject();
                parActivityObject.setActivityId(parActivity.getId());
                parActivityObject.setOrganizationId(sysDistrict.getDistrictId());
                parActivityObject.setAttachTo(sysDistrict.getAttachTo());
                Optional<ParActivityPerform> byActivityIDAndOrganizationId = parActivityPerformRepository.findByActivityIDAndOrganizationId(parActivity.getId(), sysDistrict.getId());
                if (byActivityIDAndOrganizationId.isPresent()) {
                    ParActivityPerform parActivityPerform = byActivityIDAndOrganizationId.get();
                    parActivityObject.setStatus(parActivityPerform.getStatus());
                    parActivityObject.setSource(parActivityPerform.getSource());
                } else {
                    parActivityObject.setStatus("0");
                }
                super.save(parActivityObject);
            }
        }
    }

    /**
     * 电视端，手机端截图执行，根据phoneOrTv来判断
     * @param parActivityObjectDTO
     * @param sort
     * @return
     */
    @Override
    @Transactional
    public List<ParActivityObjectVO> execute(ParActivityObjectDTO parActivityObjectDTO, Sort sort){
        if(StringUtils.isEmpty(parActivityObjectDTO.getActivityId()) ||StringUtils.isEmpty(parActivityObjectDTO.getOrganizationId())  ){
            return null;
        }
        //更新Object
        ParActivityObject parActivityObject =parActivityObjectRepository.findByOrganizationIdAndActivityId(parActivityObjectDTO.getOrganizationId(),parActivityObjectDTO.getActivityId());
        parActivityObject.setStatus("1");
        if(parActivityObjectDTO.getPhoneOrTv().equals("phone")){
            parActivityObject.setIsWorking("0");
        }else {
            parActivityObject.setIsWorking("1");
        }
        if(!StringUtils.isEmpty(parActivityObject.getSource())){
            if(parActivityObject.getSource() == 1){
                parActivityObject.setSource(2);
            }else{
                parActivityObject.setSource(1);
            }
        }else {
            parActivityObject.setSource(1);
        }

        ParActivityObject newObject = super.save(parActivityObject);

        //取得组织长短ID
        SysDistrict districtId = sysDistrictRepository.findSysDistrictByDistrictId(parActivityObjectDTO.getOrganizationId());
        Optional<ParActivityPerform> parActivityPerform = parActivityPerformRepository.findByActivityIDAndOrganizationId(parActivityObjectDTO.getActivityId(),districtId.getId());
        //判断是否perform有值，有：更新SOURCE
        if(parActivityPerform.isPresent()){
            ParActivityPerform parPerform = parActivityPerform.get();
            parPerform.setStatus("1");
            parPerform.setSource(2);
            parActivityPerformRepository.save(parPerform);
        }//无：新增
        else {
            ParActivityPerform perform = new ParActivityPerform();
            perform.setOrganizationId(districtId.getId());
            perform.setStatus("1");
            perform.setActivityID(parActivityObjectDTO.getActivityId());
            perform.setSource(1);
            parActivityPerformRepository.save(perform);
        }

        //更新前先删除截图
        if(parActivityObjectDTO.getPhoneOrTv().equals("phone")) {
            List<SysUser> list = sysUserRepository.findAllByOrganizationId(districtId.getId());
            List<String> feedBackIds = new ArrayList<>();
            for(int i=0;i<list.size();i++){
               List<ParActivityFeedback> listFeed = parActivityFeedbackRepository.findAllBySnIdAndUserId(parActivityObjectDTO.getActivityId(),list.get(i).getId());
                for(int j=0;j<listFeed.size();j++){
                    feedBackIds.add(listFeed.get(j).getId());
                }
            }
            for(int i = 0;i<feedBackIds.size();i++){
                parActivityPictureRepository.deleteAllByActivityID(feedBackIds.get(i));
            }
        }else {
            parPictureInfroRepository.deleteAllByStudyContentAndOrganizationId(parActivityObjectDTO.getActivityId(),districtId.getId());
        }

        //手机端执行,传入UserId,以及img
        if(parActivityObjectDTO.getPhoneOrTv().equals("phone")) {
            ParActivityFeedback parActivityFeedback = new ParActivityFeedback();
            parActivityFeedback.setSnId(parActivityObjectDTO.getActivityId());
            parActivityFeedback.setUserId(parActivityObjectDTO.getUserId());
            LocalDateTime localDateTime = LocalDateTime.now();
            parActivityFeedback.setTime(localDateTime);
            parActivityFeedback.setFlag("1");

            ParActivityFeedback addParActivityFeedBack = parActivityFeedbackRepository.save(parActivityFeedback);

            for (String s : parActivityObjectDTO.getPhoneImgList()) {
                ParActivityPicture parActivityPicture = new ParActivityPicture();
                parActivityPicture.setActivityID(addParActivityFeedBack.getId());
                parActivityPicture.setImageUrl(s);
                parActivityPictureRepository.save(parActivityPicture);
            }

        }

            SysConfiguration sysConfiguration = sysConfigurationService.findById("b19abd37-df80-4f73-8e8a-de2064720c7e");
        String codeValue = sysConfiguration.getCodeValue();

        //redis存入摄像头数据number
        if(!StringUtils.isEmpty(parActivityObjectDTO.getNumber())){
            redisTemplate.boundValueOps(parActivityObjectDTO.getOrganizationId()).set(parCameraRepository.findByNumber(parActivityObjectDTO.getNumber()).getIP());
        }

        ParActivityObjectVO convert = newObject.convert(ParActivityObjectVO.class);
        convert.setCodeValue(codeValue);
        List<ParActivityObjectVO> list = new ArrayList<>();
        list.add(convert);
        return list;
    }
    @Override
    @Transactional
    public List<ParActivityObjectVO> executeOver(ParActivityObjectDTO parActivityObjectDTO, Sort sort) {
        if (StringUtils.isEmpty(parActivityObjectDTO.getActivityId()) || StringUtils.isEmpty(parActivityObjectDTO.getOrganizationId())) {
            return null;
        }
        //更新Object
        ParActivityObject parActivityObject =parActivityObjectRepository.findByOrganizationIdAndActivityId(parActivityObjectDTO.getOrganizationId(),parActivityObjectDTO.getActivityId());
        parActivityObject.setIsWorking("0");
        ParActivityObject newObject = super.save(parActivityObject);

        redisTemplate.delete(parActivityObjectDTO.getOrganizationId());
        System.out.println(redisTemplate.boundValueOps("name").get());
        ParActivityObjectVO convert = newObject.convert(ParActivityObjectVO.class);
        List<ParActivityObjectVO> list = new ArrayList<>();
        list.add(convert);
        //消息中心添加
        messageCenterService.save(parActivityObjectDTO.getActivityId(),parActivityObjectDTO.getOrganizationId(),"party");
        return list;
    }

    @Override
    public List<ParActivityObjectVO> TVIndexDetailList(String number) {
        ParCamera byNumber = parCameraService.findByNumber(number);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ParActivityObject.class);
        detachedCriteria.add(Restrictions.eq("organizationId", byNumber.getSysDistrict().getDistrictId()));
        detachedCriteria.createAlias("parActivity", "p");
        detachedCriteria.add(Restrictions.between("p.month", firstDay(), lastDay()));
        return ParActivityObject.convert(findAll(detachedCriteria), ParActivityObjectVO.class);
    }

    @Override
    public Integer waitCheckNumber(String organizationId) {
        Integer integer = parActivityObjectRepository.countAllByOrganizationIdStartingWithAndStatus(organizationId, "1");
        return integer;
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
