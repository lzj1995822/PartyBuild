package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.constant.AuthorizationConstants;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.base.utils.TokenUtil;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectSearchable;
import com.cloudkeeper.leasing.identity.dto.parpictureinfro.ParPictureInfroSearchable;
import com.cloudkeeper.leasing.identity.repository.*;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.*;
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
import java.time.*;
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

    @Autowired
    private ParActivityObjectService parActivityObjectService;

    /**
     * 电视截图
     */
    @Autowired
    private ParPictureInfroRepository parPictureInfroRepository;
    @Autowired
    private ParPictureInfroService parPictureInfroService;

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
                .withMatcher("objectType", ExampleMatcher.GenericPropertyMatchers.contains())
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
    public BigDecimal handleActivityCompleteRate(String organizationId, String year) {
        Integer yearNum = Integer.valueOf(year);
        String sql = "SELECT  COUNT( CASE WHEN pao.status = 2 THEN 1 ELSE NULL END ) as finished, COUNT(pao.id) as total FROM PAR_ActivityObject pao " +
                " LEFT JOIN PAR_Activity pa on pa.id = pao.activityId WHERE pao.organizationId like " +
                " '" + organizationId + "%' And pa.month BETWEEN '" +year+ "-01-01' and '" + getLastDayByYear(yearNum).toString() + "'";
        CalNumberVO bySql = findBySql(CalNumberVO.class, sql);
        BigDecimal total = new BigDecimal(bySql.getTotal());
        BigDecimal finished = new BigDecimal(bySql.getFinished());
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
            parPerform.setModifiedAt(LocalDateTime.now());
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
//            List<SysUser> list = sysUserRepository.findAllByOrganizationId(districtId.getId());
//            List<String> feedBackIds = new ArrayList<>();
//            for(int i=0;i<list.size();i++){
//               List<ParActivityFeedback> listFeed = parActivityFeedbackRepository.findAllBySnIdAndUserId(parActivityObjectDTO.getActivityId(),list.get(i).getId());
//                for(int j=0;j<listFeed.size();j++){
//                    feedBackIds.add(listFeed.get(j).getId());
//                }
//            }
//            for(int i = 0;i<feedBackIds.size();i++){
//                parActivityPictureRepository.deleteAllByActivityID(feedBackIds.get(i));
//            }
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

    //    指定年份当前月最后一天
    private LocalDate getLastDayByYear(Integer year) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, year);
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
    @Override
    public  List<ExamScoreDetailVO> examScoreDetail(String districtName, String year){
        List<ExamScoreDetailVO> exD = new ArrayList<>();
        SysDistrict sysDistrict = sysDistrictRepository.findByDistrictName(districtName);
        String districtId = sysDistrict.getDistrictId();
        String sql = "SELECT " +
                "S1.*, " +
                "S2.districtName  " +
                "FROM " +
                "( " +
                "SELECT " +
                "p1.organizationId, " +
                "p1.activityId, " +
                "p2.title, " +
                "p2.context, " +
                "p1.STATUS sta, " +
                "p2.score, " +
                "p2.month "+
                "FROM " +
                "PAR_ActivityObject p1 " +
                "LEFT JOIN PAR_Activity p2 ON p1.activityId = p2.id  " +
                "AND organizationId = '"+districtId+"'  " +
                "WHERE " +
                "p2.MONTH BETWEEN '"+year+"-1-1'  " +
                "AND '"+year+"-12-30'  " +
                ") S1," +
                "SYS_District S2  " +
                "WHERE " +
                "S1.organizationId = S2.districtID ORDER BY month asc";
        exD = super.findAllBySql(ExamScoreDetailVO.class ,sql);
        return exD;
    }

  /*  private long calculateTime(String districtId,String activityId){
        ParPictureInfroSearchable search = new ParPictureInfroSearchable();
        List<SysDistrict> allByDistrictId = sysDistrictRepository.findAllByDistrictId(districtId);
        search.setStudyContent(activityId);
        search.setOrganizationId(allByDistrictId.get(0).getId());
        List<ParPictureInfro> all = parPictureInfroService.findAll(search,new Sort(Sort.Direction.DESC, "createTime"));
        long currentTime = System.currentTimeMillis();
        long newPictureTime = all.get(0).getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long timeInterval = currentTime - newPictureTime;
        return timeInterval;
    }
*/
    @Override
    public void updateIsWorking() {
       /* ParActivityObjectSearchable search = new ParActivityObjectSearchable();
        search.setIsWorking("1");
        List<ParActivityObject> all = super.findAll(search);
        if(all.size()>0){
            for(ParActivityObject item: all) {
                long temp = calculateTime(item.getOrganizationId(), item.getActivityId());
                if(temp>3700000){
                    item.setIsWorking("0");
                    parActivityObjectRepository.save(item);
                }
            }
        }*/

        String sql = "SELECT o.id as objectId, max(p.CreateTime) as newTime from  PAR_ActivityObject AS o " +
                "LEFT JOIN SYS_District AS s ON s.districtId = o.organizationId " +
                "INNER JOIN PAR_Picture_Infro AS p ON p.StudyContent = o.activityId AND p.organizationId = s.id " +
                "WHERE o.isWorking=1 " +
                "GROUP BY o.id";
        List<ActivityIsWorkingVO> objectList = super.findAllBySql(ActivityIsWorkingVO.class, sql);
        if(objectList.size()>0){
            objectList.forEach( item -> {
                long currentTime = System.currentTimeMillis();
                long newPictureTime = item.getNewTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
                long timeInterval = currentTime - newPictureTime;
                if(timeInterval>370000){
                    Optional<ParActivityObject> parActivityObject = parActivityObjectRepository.findById(item.getObjectId());
                    ParActivityObject oldObject = parActivityObject.get();
                    oldObject.setIsWorking("0");
                    save(oldObject);
                }
            });
        }

    }

    @Override
    public List<CloudActivityRateVO> townMonthRate() {
        String sql = "SELECT temp.*,s.districtName,s.location,ROUND(cast(temp.finished AS FLOAT)/temp.total, 2) AS rate " +
                "from (select attachTo,count( CASE WHEN temptable.status = 2 THEN 1 ELSE NULL END) finished ,COUNT(*) as total " +
                "from (SELECT o.id, o.activityId,o.status,o.organizationId,o.attachTo,a.title,a.month from PAR_ActivityObject as o  " +
                "LEFT JOIN PAR_Activity AS a ON a.id = o.activityId  " +
                "WHERE YEAR(a.month) = YEAR(GETDATE()) AND MONTH(a.month) = MONTH(GETDATE())) as temptable  " +
                "GROUP BY attachTo) as temp  " +
                "LEFT JOIN SYS_District as s ON s.districtID = temp.attachTo";
        List<CloudActivityRateVO> allBySql = super.findAllBySql(CloudActivityRateVO.class, sql);
        return allBySql;
    }

    @Override
    public List<CloudActivityCunFinishedVO> cunMonthObject(String attachTo) {
        String sql = "SELECT temp.*,s.districtName,s.location  " +
                "FROM (select organizationId,count( CASE WHEN temptable.status = 2 THEN 1 ELSE NULL END) finished ,count( CASE WHEN temptable.status !=2 THEN 1 ELSE NULL END) unfinished  " +
                "from (SELECT o.id, o.activityId,o.status,o.organizationId,o.attachTo,a.title,a.month from PAR_ActivityObject as o " +
                "LEFT JOIN PAR_Activity AS a ON a.id = o.activityId  " +
                "WHERE YEAR(a.month) = YEAR(GETDATE()) AND MONTH(a.month) = MONTH(GETDATE()) AND o.attachTo= "+ attachTo + ") as temptable  " +
                "GROUP BY organizationId) as temp " +
                "LEFT JOIN SYS_District as s ON s.districtID = organizationId";
        List<CloudActivityCunFinishedVO> allBySql = super.findAllBySql(CloudActivityCunFinishedVO.class, sql);
        return allBySql;
    }

    @Override
    public Integer countActivityPassNumber() {
        String sql = "select count(1) as number from PAR_ActivityObject where CONVERT(varchar(10),modifiedAt,120) = CONVERT(varchar(10),GETDATE(),120) AND status = 2";
        List<CloudIntegerVO> bySql = super.findAllBySql(CloudIntegerVO.class, sql);
        return bySql.get(0).getNumber();
    }

    @Override
    public Integer countActivityIsWorkingNumber() {
      /*  String  sql = "select count (1) from PAR_ActivityObject where isWorking = 1";
        List<String> bySql = super.findAllBySql(String.class, sql);
        Integer  a  = Integer.parseInt(bySql.get(0));*/
        Integer integer = parActivityObjectRepository.countAllByIsWorking("2");
        return integer;
    }

}
