package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.dto.familyinfo.FamilyInfoDTO;
import com.cloudkeeper.leasing.identity.dto.familyworkinfo.FamilyWorkInfoDTO;
import com.cloudkeeper.leasing.identity.dto.honourinfo.HonourInfoDTO;
import com.cloudkeeper.leasing.identity.dto.rewardinfo.RewardInfoDTO;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictSearchable;
import com.cloudkeeper.leasing.identity.dto.traininginfo.TrainingInfoDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import com.cloudkeeper.leasing.identity.repository.VillageCadresRepository;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.CadresExamineVO;
import com.cloudkeeper.leasing.identity.vo.CadresGroupByLevelVO;
import com.cloudkeeper.leasing.identity.vo.CadresStatisticsVO;
import com.cloudkeeper.leasing.identity.vo.SecretaryNumberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 村干部管理 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageCadresServiceImpl extends BaseServiceImpl<VillageCadres> implements VillageCadresService {

    /**
     * 村干部管理 repository
     */
    private final VillageCadresRepository villageCadresRepository;

    private final InformationAuditService informationAuditService;

    private final CadrePositionService cadrePositionService;

    private final SysDistrictService sysDistrictService;

    private final HonourInfoService honourInfoService;

    private final RewardInfoService rewardInfoService;

    private final RatingStandardService ratingStandardService;

    private final CadreTaskService cadreTaskService;

    private final CadreTaskObjectService cadreTaskObjectService;

    private final FamilyInfoService familyInfoService;

    private final FamilyWorkInfoService familyWorkInfoService;

    private final TrainingInfoService trainingInfoService;

    private final SysUserService sysUserService;

    private final VillageCadresTermService villageCadresTermService;

    @Override
    protected BaseRepository<VillageCadres> getBaseRepository() {
        return villageCadresRepository;
    }

    @Autowired
    private MessageCenterService messageCenterService;

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("sex", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nation", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("health", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nativePlace", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("birthPlace", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("education", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("identityCard", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("postExperience", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("hasRetire", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quasiAssessmentRank", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cadresType", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("fullTimeEdu", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public Long countAllByDistrictId(String districtId) {
        Long aLong = villageCadresRepository.countAllByDistrictIdStartingWith(districtId);
        return aLong;
    }

    @Override
    public void initPost() {
        List<VillageCadres> all = villageCadresRepository.findAll();
        all.forEach(item -> {
            CadrePosition cadrePosition = new CadrePosition();
            cadrePosition.setDistrictId(item.getDistrictId());
            cadrePosition.setCadreId(item.getId());
            if (!StringUtils.isEmpty(item.getSysDistrict())) {
                cadrePosition.setPost(item.getSysDistrict().getDistrictName() + item.getDuty());
            }
            cadrePositionService.save(cadrePosition);
        });

    }

    @Override
    @Transactional
    public VillageCadres save(VillageCadresDTO villageCadresDTO) {
        String cadresType = villageCadresDTO.getCadresType();
        if (StringUtils.isEmpty(cadresType)) {
            return null;
        }
        CadreTask currentBaseInfoTask = new CadreTask();
        String msg = "更新了该村书记信息！";
        if (cadresType.contains("IN_SECRETARY")) {
            // 检查是否有当前任务
            currentBaseInfoTask = cadreTaskService.getSecretaryTask();
            msg = "更新了该村干部信息！";
        } else {
            currentBaseInfoTask = cadreTaskService.getCurrentBaseInfoTask();
        }
        if (currentBaseInfoTask == null) {
            return null;
        }
        VillageCadres convert = villageCadresDTO.convert(VillageCadres.class);

        SysDistrict byDistrictId = sysDistrictService.findByDistrictId(villageCadresDTO.getDistrictId());
        convert.setParentDistrictId(byDistrictId.getOrgParent());
        convert.setParentDistrictName(byDistrictId.getParentName());
        convert.setDistrictName(byDistrictId.getDistrictName());

        // 无论更新还是新增，只要调用新增或者更新接口，之前的审核流程需要重新走，所以初始化成0（未提交）
        convert.setState("0");
        // 主表更新
        convert = super.save(convert);
        // 状态记录表中，新增初始状态记录，未提交状态
        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setVillageId(convert.getId());
        informationAudit.setStatus(convert.getState());
        informationAudit.setTaskId(currentBaseInfoTask.getId());
        informationAudit.setProcessType(currentBaseInfoTask.getType());
        informationAudit.setAuditor(convert.getParentDistrictName());
        informationAudit.setAuditAdvice(msg);
        informationAuditService.save(informationAudit);

        // 删除奖惩
        String cadresId = convert.getId();
        List<HonourInfoDTO> honours = villageCadresDTO.getHonours();
        honourInfoService.deleteAllByCadresId(cadresId);
        for (HonourInfoDTO item:honours) {
            item.setCadresId(cadresId);
            item.setDistrictId(villageCadresDTO.getDistrictId());
            honourInfoService.save(item.convert(HonourInfo.class));
        }

        // 删除报酬
        List<RewardInfoDTO> rewards = villageCadresDTO.getRewards();
        rewardInfoService.deleteAllByCadresId(cadresId);
        for (RewardInfoDTO item:rewards) {
            item.setCadresId(cadresId);
            rewardInfoService.save(item.convert(RewardInfo.class));
        }


        List<FamilyInfoDTO> familyInfos = villageCadresDTO.getFamilyInfoDTOS();
        familyInfoService.deleteAllByCadresId(cadresId);
        for (FamilyInfoDTO f : familyInfos){
            f.setCadresId(cadresId);
            familyInfoService.save(f.convert(FamilyInfo.class));
        }

        List<FamilyWorkInfoDTO> familyWorkInfos = villageCadresDTO.getFamilyWorkInfoDTOS();
        familyWorkInfoService.deleteAllByCadresId(cadresId);
        for (FamilyWorkInfoDTO f : familyWorkInfos){
            f.setCadresId(cadresId);
            familyWorkInfoService.save(f.convert(FamilyWorkInfo.class));
        }

        List<TrainingInfoDTO> trainingInfoDTOS = villageCadresDTO.getTrainingInfoDTOS();
        trainingInfoService.deleteAllByCadresId(cadresId);
        for (TrainingInfoDTO f : trainingInfoDTOS){
            f.setCadresId(cadresId);
            trainingInfoService.save(f.convert(TrainingInfo.class));
        }
        return convert;
    }

    @Override
    public VillageCadres saveBaseInfo(VillageCadresDTO villageCadresDTO) {
        // 已存在系统中的干部和新岗位维护关系
        if (!StringUtils.isEmpty(villageCadresDTO.getCadresId()) && !StringUtils.isEmpty(villageCadresDTO.getPositionId())) {
            Optional<CadrePosition> optionalById = cadrePositionService.findOptionalById(villageCadresDTO.getPositionId());
            if (!optionalById.isPresent()) {
                return null;
            }
            Optional<VillageCadres> byId = villageCadresRepository.findById(villageCadresDTO.getCadresId());
            if (!byId.isPresent()) {
                return null;
            }
            VillageCadres villageCadres = byId.get();
            CadrePosition cadrePosition = optionalById.get();
            cadrePosition.setCadreId(villageCadres.getId());
            cadrePositionService.save(cadrePosition);
            return villageCadres;
        }
        // 新增干部的处理
        VillageCadres convert = villageCadresDTO.convert(VillageCadres.class);
        SysDistrict byDistrictId = sysDistrictService.findByDistrictId(villageCadresDTO.getDistrictId());
        convert.setParentDistrictId(byDistrictId.getOrgParent());
        convert.setParentDistrictName(byDistrictId.getParentName());
        convert.setDistrictName(byDistrictId.getDistrictName());
        convert = super.save(convert);
        Optional<CadrePosition> cadrePosition = cadrePositionService.findOptionalById(villageCadresDTO.getPositionId());
        if (!cadrePosition.isPresent()) {
            return null;
        }
        CadrePosition cadrePosition1 = cadrePosition.get();
        cadrePosition1.setCadreId(convert.getId());
        cadrePositionService.save(cadrePosition1);

        //添加村干部任期信息----开始
        VillageCadresTerm villageCadresTerm = new VillageCadresTerm();
        villageCadresTerm.setCadresId(convert.getId());
        villageCadresTerm.setCadresName(convert.getName());
        villageCadresTerm.setAppointmentTime(LocalDate.now());
        villageCadresTerm.setDistrictId(convert.getDistrictId());
        villageCadresTerm.setCadresType(convert.getCadresType());
        villageCadresTerm.setDistrictName(convert.getDistrictName());
        villageCadresTerm.setTermType("0");
        villageCadresTermService.save(villageCadresTerm);
        //添加村干部任期信息----结束

        return convert;
    }

    @Override
    public Boolean submit(VillageCadres villageCadres) {
        String cadresType = villageCadres.getCadresType();
        if (StringUtils.isEmpty(cadresType)) {
            return false;
        }
        CadreTask currentBaseInfoTask = new CadreTask();
        if (!cadresType.contains("IN_SECRETARY")) {
            // 检查是否有当前任务
            currentBaseInfoTask = cadreTaskService.getCurrentBaseInfoTask();
            if (currentBaseInfoTask == null) {
                return false;
            }
        }

        villageCadres.setState("2");
        villageCadres = villageCadresRepository.save(villageCadres);

        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setVillageId(villageCadres.getId());
        informationAudit.setTaskId(currentBaseInfoTask.getId());
        informationAudit.setProcessType(currentBaseInfoTask.getType());
        informationAudit.setStatus(villageCadres.getState());
        informationAudit.setAuditor(villageCadres.getParentDistrictName());
        informationAudit.setAuditAdvice("提交了该村书记信息！");
        informationAuditService.save(informationAudit);

        return true;
    }

    @Override
    public Boolean virify(String id, String code, InformationAuditDTO informationAuditDTO2 ) {
        Optional<VillageCadres> byId = villageCadresRepository.findById(id);
        if(!byId.isPresent()){
            return false;
        }
        String cadresType = byId.get().getCadresType();
        if (StringUtils.isEmpty(cadresType)) {
            return false;
        }

        CadreTask currentBaseInfoTask = new CadreTask();
        if (!cadresType.contains("IN_SECRETARY")) {
            // 检查是否有当前任务
            currentBaseInfoTask = cadreTaskService.getCurrentBaseInfoTask();
            if (currentBaseInfoTask == null) {
                return false;
            }
        }

        VillageCadres villageCadres = byId.get();
        String currentState = villageCadres.getState();
        Integer integer = Integer.valueOf(currentState);

        String districtId = new String();
        if(villageCadres.getState().equals("1")){
            districtId = villageCadres.getDistrictId();
        }else if (villageCadres.getState().equals("2")){
            districtId = villageCadres.getDistrictId().substring(0,4);
        }else if(villageCadres.getState().equals("3")){
            districtId=villageCadres.getDistrictId().substring(0,4);
        }

        String checkMsg = new String();
        /*判断前端传来的提交 villageId ,code是否通过*/
        if (code.equals("SUCCESS")) {
            integer++;
            checkMsg = "审核通过意见:";
            //只有村书记才生成职级评定标准
            if (integer == 3 && villageCadres.getCadresType().equals("SECRETARY")) {
                // 如果市委审核通过后，把表彰和报酬不可更改
                this.updateIsEdit(id);
                RatingStandard actual = this.generatePostLevel(villageCadres);
                RatingStandard checkRes = ratingStandardService.checkEnter(actual);
                if (checkRes != null) {
                    actual.setName(checkRes.getName());
                    String quasiAssessmentRank = villageCadres.getQuasiAssessmentRank();
                    if (!StringUtils.isEmpty(quasiAssessmentRank)) {
                        RatingStandard standard = ratingStandardService.findByNameAndIsStandard(quasiAssessmentRank, "1");
                        if (checkRes.getStandardValue() > standard.getStandardValue()) {
                            actual.setPromotable("1");
                        } else {
                            actual.setPromotable("0");
                        }
                    } else {
                        actual.setPromotable("1");
                    }
                }
                actual.setCadresId(id);
                actual.setIsStandard("0");
                actual.setEnable("1");
                actual.setDistrictId(villageCadres.getDistrictId());
                ratingStandardService.deleteByCadresId(id);
                ratingStandardService.save(actual);
            }
        } else {
            integer = integer - 2;
            checkMsg = "审核驳回意见:";
        }
        villageCadres.setState(integer.toString());
        villageCadres = villageCadresRepository.save(villageCadres);

        Optional<SysUser> optionalById = sysUserService.findOptionalById(getCurrentPrincipalId());
        if (!optionalById.isPresent()) {
            return null;
        }
        String districtName = optionalById.get().getName();
        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setStatus(villageCadres.getState());
        informationAudit.setVillageId(id);
        informationAudit.setTaskId(currentBaseInfoTask.getId());
        informationAudit.setProcessType(currentBaseInfoTask.getType());
        informationAudit.setAuditAdvice("审核意见：" + informationAuditDTO2.getAuditAdvice());
        informationAudit.setAuditor(districtName + "-" + informationAuditDTO2.getAuditor());
        informationAuditService.save(informationAudit);

        cadreTaskObjectService.updateStatusByTaskIdAndObjectId(currentBaseInfoTask.getId(), villageCadres.getParentDistrictId());

        messageCenterService.save(villageCadres.getId(),districtId,
                "[村书记信息]" + villageCadres.getName() + checkMsg + informationAuditDTO2.getAuditAdvice());
        return true;
    }

    // 村干部提交
    public Boolean secretarySubmit(VillageCadres villageCadres) {
        String cadresType = villageCadres.getCadresType();
        if (StringUtils.isEmpty(cadresType)) {
            return false;
        }
        CadreTask currentBaseInfoTask = cadreTaskService.getSecretaryTask();
        if (currentBaseInfoTask == null) {
            return false;
        }

        villageCadres.setState("1");
        villageCadres = villageCadresRepository.save(villageCadres);

        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setVillageId(villageCadres.getId());
        informationAudit.setTaskId(currentBaseInfoTask.getId());
        informationAudit.setProcessType(currentBaseInfoTask.getType());
        informationAudit.setStatus(villageCadres.getState());
        informationAudit.setAuditor(villageCadres.getParentDistrictName());
        informationAudit.setAuditAdvice("提交了该村干部信息！");
        informationAuditService.save(informationAudit);

        return true;
    }

    // 村干部审核
    public Boolean secretaryReview(String id, String code, InformationAuditDTO informationAuditDTO2) {
        Optional<VillageCadres> byId = villageCadresRepository.findById(id);
        if(!byId.isPresent()){
            return false;
        }
        String cadresType = byId.get().getCadresType();
        if (StringUtils.isEmpty(cadresType)) {
            return false;
        }

        // 检查是否有当前任务
        CadreTask currentBaseInfoTask = cadreTaskService.getSecretaryTask();
        if (currentBaseInfoTask == null) {
            return false;
        }

        VillageCadres villageCadres = byId.get();
        String currentState = villageCadres.getState();
        Integer integer = Integer.valueOf(currentState);

        String districtId = new String();
        if (villageCadres.getState().equals("2")){
            districtId = villageCadres.getDistrictId().substring(0,4);
        }else if(villageCadres.getState().equals("3")){
            districtId=villageCadres.getDistrictId().substring(0,2);
        }

        String checkMsg = new String();
        /*判断前端传来的提交 villageId ,code是否通过*/
        if (code.equals("SUCCESS")) {
            integer++;
            checkMsg = "审核通过意见:";
        } else {
            integer = 0;
            checkMsg = "审核驳回意见:";
        }
        villageCadres.setState(integer.toString());
        villageCadres = villageCadresRepository.save(villageCadres);

        Optional<SysUser> optionalById = sysUserService.findOptionalById(getCurrentPrincipalId());
        if (!optionalById.isPresent()) {
            return false;
        }
        String districtName = optionalById.get().getName();
        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setStatus(villageCadres.getState());
        informationAudit.setVillageId(id);
        informationAudit.setTaskId(currentBaseInfoTask.getId());
        informationAudit.setProcessType(currentBaseInfoTask.getType());
        informationAudit.setAuditAdvice("审核意见：" + informationAuditDTO2.getAuditAdvice());
        informationAudit.setAuditor(districtName + "-" + informationAuditDTO2.getAuditor());
        informationAuditService.save(informationAudit);
        messageCenterService.save(villageCadres.getId(),districtId,
                "[村书记信息]" + villageCadres.getName() + checkMsg + informationAuditDTO2.getAuditAdvice());
        return true;
    }

    @Override
    public SecretaryNumberVO countNumber() {
        SecretaryNumberVO secretaryNumberVO = new SecretaryNumberVO();
        secretaryNumberVO.setLevelOne(villageCadresRepository.countAllByQuasiAssessmentRank("FIRST_CLASS"));
        secretaryNumberVO.setLevelTwo(villageCadresRepository.countAllByQuasiAssessmentRank("SECOND_CLASS"));
        secretaryNumberVO.setLevelThree(villageCadresRepository.countAllByQuasiAssessmentRank("THIRD_CLASS"));
        secretaryNumberVO.setLevelFour(villageCadresRepository.countAllByQuasiAssessmentRank("FOUR_CLASS"));
        secretaryNumberVO.setLevelFive(villageCadresRepository.countAllByQuasiAssessmentRank("FIRTH_CLASS"));
        return secretaryNumberVO;
    }

    @Override
    public List<CadresExamineVO> getExamines(String cadresType1, String districtId1) {
        String cadresType = "SECRETARY";
        String districtId = "01";
        String queryStatus = "2";
        if (!StringUtils.isEmpty(cadresType1)) {
            cadresType = cadresType1;
            // 村干部并且镇审
            if (cadresType1.equals("IN_SECRETARY") && districtId1.length() == 4){
                queryStatus = "1";
            }
        }
        if (!StringUtils.isEmpty(districtId1)) {
            districtId = districtId1;
        }
        String sql = "SELECT cadres.id,cadres.name, cadres.parentDistrictName, info.modifiedAt FROM Information_Audit info" +
                " JOIN village_cadres cadres ON info.villageId = cadres.id and info.status = '" +queryStatus + "'  JOIN " +
                " (select audit.villageId, max(audit.modifiedAt) as modifiedAt  from Information_Audit audit  " +
                " GROUP BY audit.villageId ) a on info.villageId = a.villageId and info.modifiedAt = a.modifiedAt " +
                " where cadres.hasRetire = 0 and cadres.cadresType like '" + cadresType + "%' and cadres.districtId like '" + districtId + "%'";
        String sqlCount = "SELECT cadres.parentDistrictName as districtName, count(1) as number FROM Information_Audit " +
                " info JOIN village_cadres cadres ON info.villageId = cadres.id and info.status = '" +queryStatus + "'  JOIN " +
                " (select audit.villageId, max(audit.modifiedAt) as modifiedAt  from Information_Audit audit " +
                " GROUP BY audit.villageId ) a on info.villageId = a.villageId and info.modifiedAt = a.modifiedAt " +
                "  where cadres.hasRetire = 0 and cadres.cadresType like '" + cadresType + "%' and cadres.districtId like '" + districtId + "%' " +
                "group by cadres.parentDistrictName";
        List<CadresStatisticsVO> villageCadres = findAllBySql(CadresStatisticsVO.class,sql);
        List<CadresExamineVO> list = findAllBySql(CadresExamineVO.class,sqlCount);
        for (CadresExamineVO c : list){
            for (CadresStatisticsVO v : villageCadres){
                if (c.getDistrictName().equals(v.getParentDistrictName())){
                    if (c.getIds() == null){
                        List<Map<String,String>> str = new ArrayList<>();
                        Map<String,String> map = new HashMap<>();
                        map.put("id",v.getId());
                        map.put("name",v.getName());
                        str.add(map);
                        c.setIds(str);
                    }else {
                        List<Map<String,String>> str = c.getIds();
                        Map<String,String> map = new HashMap<>();
                        map.put("id",v.getId());
                        map.put("name",v.getName());
                        str.add(map);
                        c.setIds(str);
                    }
                }
            }
        }
        return list;
    }

    @Override
    @Transactional
    public void updateIsEdit(String cadresId) {
        List<HonourInfo> honours = honourInfoService.findAllByCadresId(cadresId);
        for (HonourInfo item : honours) {
            item.setIsEdit("1");
            honourInfoService.save(item);
        }

        List<RewardInfo> rewards = rewardInfoService.findAllByCadresId(cadresId);
        for (RewardInfo item : rewards) {
            item.setIsEdit("1");
            rewardInfoService.save(item);
        }
    }

    @Override
    public RatingStandard generatePostLevel(VillageCadres villageCadres) {
        RatingStandard ratingStandard = new RatingStandard();
        // 村书记时长
        ratingStandard.setWorkDuration(villageCadres.getOnDutyTime());
        // 能力研判
        if (!StringUtils.isEmpty(villageCadres.getEvaluation())) {
            ratingStandard.setAbilityJudgement(villageCadres.getEvaluation());
        }
        // 上年度专职村书记考核等次
        ratingStandard.setLastGrade("1");
        // 年度考核获得称职及以上等次累计次数
        // 年度考核获得称职及以上等次连续年数
        // 年度考核获得优秀等次累计次数
        // 年度考核获得优秀等次连续年数
        // 年度考核全市排名前10的连续年数
        // 年度考核全市排名前5的连续年数
        // 年度考核全市排名前3的连续年数
        // 表彰等级(所有表彰类型数组拼接字符串)
        List<HonourInfo> honourInfos = villageCadres.getHonourInfos();
        if (honourInfos != null && honourInfos.size() != 0) {
            ratingStandard.setHonoursType(String.join("," ,villageCadres.getHonourInfos().stream().map(HonourInfo::getHonourType).collect(Collectors.toList())));
        }
        return ratingStandard;
    }

    @Override
    public List<VillageCadres> findAllByParentDistrictId(String objectId) {
        return villageCadresRepository.findAllByParentDistrictId(objectId);
    }

    @Override
    public void initCadres() {
        SysDistrictSearchable sysDistrictSearchable = new SysDistrictSearchable();
        sysDistrictSearchable.setDistrictLevel(3);
        sysDistrictSearchable.setDistrictType("Party");
        List<SysDistrict> all = sysDistrictService.findAll(sysDistrictSearchable);
        for (SysDistrict item : all) {
            VillageCadres villageCadres = new VillageCadres();
            villageCadres.setName(item.getDistrictName() +"村书记");
            villageCadres.setDistrictId(item.getDistrictId());
            villageCadres.setParentDistrictId(item.getOrgParent());
            villageCadres.setCadresType("SECRETARY");
            villageCadresRepository.save(villageCadres);
        }
    }

    @Override
    public List<CadresGroupByLevelVO> getCadresGroupByLevel(String districtId) {
        List<String> levels = new ArrayList();
        levels.add("一级专职村书记");
        levels.add("二级专职村书记");
        levels.add("三级专职村书记");
        levels.add("四级专职村书记");
        levels.add("五级专职村书记");
        List<CadresGroupByLevelVO> res = new ArrayList<>();
        for (String item: levels) {
            List<VillageCadres> allByQuasiAssessmentRank;
            if (districtId == null) {
                allByQuasiAssessmentRank = findAllByQuasiAssessmentRankAndHasRetire(item, "0");
            } else {
                allByQuasiAssessmentRank = findAllByQuasiAssessmentRankAndParentDistrictIdAndHasRetire(item, districtId, "0");
            }
            List<Map<String, String>> values = new ArrayList<>();
            for (VillageCadres subItem : allByQuasiAssessmentRank) {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("name", subItem.getName());
                stringStringHashMap.put("postName", subItem.getCadrePosition().stream().map(CadrePosition::getName).collect(Collectors.joining("、")));
                stringStringHashMap.put("headSculpture", subItem.getHeadSculpture());
                stringStringHashMap.put("id", subItem.getId());
                values.add(stringStringHashMap);
            }
            CadresGroupByLevelVO cadresGroupByLevelVO = new CadresGroupByLevelVO();
            String newLevelName = item.replace("专职村书记", "职级");
            cadresGroupByLevelVO.setLevelName(newLevelName);
            cadresGroupByLevelVO.setCadres(values);
            res.add(cadresGroupByLevelVO);
        }
        return res;
    }

    private List<VillageCadres> findAllByQuasiAssessmentRankAndParentDistrictIdAndHasRetire(String rank, String districtId, String hasRetire) {
        return villageCadresRepository.findAllByQuasiAssessmentRankAndParentDistrictIdAndHasRetire(rank, districtId, hasRetire);
    }

    private List<VillageCadres> findAllByQuasiAssessmentRankAndHasRetire(String rank, String hasRetire) {
        return villageCadresRepository.findAllByQuasiAssessmentRankAndHasRetire(rank, hasRetire);
    }
}
