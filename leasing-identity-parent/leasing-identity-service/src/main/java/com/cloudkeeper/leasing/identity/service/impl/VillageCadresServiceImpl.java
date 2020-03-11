package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.familyinfo.FamilyInfoDTO;
import com.cloudkeeper.leasing.identity.dto.familyworkinfo.FamilyWorkInfoDTO;
import com.cloudkeeper.leasing.identity.dto.honourinfo.HonourInfoDTO;
import com.cloudkeeper.leasing.identity.dto.rewardinfo.RewardInfoDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.repository.VillageCadresRepository;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.SecretaryNumberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
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

    private final FamilyInfoService familyInfoService;

    private final FamilyWorkInfoService familyWorkInfoService;

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
                .withMatcher("postExperience", ExampleMatcher.GenericPropertyMatchers.contains());
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
        String cadrePositionId = villageCadresDTO.getPost();
        Optional<CadrePosition> optionalById = cadrePositionService.findOptionalById(cadrePositionId);
        if (!optionalById.isPresent()) {
            return null;
        }
        VillageCadres convert = villageCadresDTO.convert(VillageCadres.class);

        SysDistrict byDistrictId = sysDistrictService.findByDistrictId(villageCadresDTO.getDistrictId());
        convert.setParentDistrictId(byDistrictId.getOrgParent());

        // 无论更新还是新增，只要调用新增或者更新接口，之前的审核流程需要重新走，所以初始化成0（未提交）
        convert.setState("0");
        convert = super.save(convert);
        CadrePosition cadrePosition = optionalById.get();
        cadrePosition.setCadreId(convert.getId());
        cadrePositionService.save(cadrePosition);

        String cadresId = convert.getId();
        List<HonourInfoDTO> honours = villageCadresDTO.getHonours();
        honourInfoService.deleteAllByCadresId(cadresId);
        for (HonourInfoDTO item:honours) {
            item.setCadresId(cadresId);
            honourInfoService.save(item.convert(HonourInfo.class));
        }

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
        return convert;
    }

    @Override
    public Boolean submit(VillageCadres villageCadres) {
        villageCadres.setState("1");
        villageCadres = villageCadresRepository.save(villageCadres);

        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setVillageId(villageCadres.getId());

        /*模拟审核通过状态*/
        informationAudit.setStatus(villageCadres.getContact());
        informationAuditService.save(informationAudit);
        return true;
    }

    @Override
    public Boolean virify(@PathVariable("id") String id, @PathVariable("code") String code, @RequestBody  InformationAuditDTO informationAuditDTO2 ) {
        Optional<VillageCadres> byId = villageCadresRepository.findById(id);
        if(!byId.isPresent()){
            return false;
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
            if (integer == 3) {
                // 如果市委审核通过后，把表彰和报酬不可更改
                this.updateIsEdit(id);
                RatingStandard actual = this.generatePostLevel(villageCadres);
                RatingStandard checkRes = ratingStandardService.checkEnter(actual);
                if (checkRes != null) {
                    actual.setName(checkRes.getName());
                    villageCadres.setQuasiAssessmentRank(checkRes.getName());
                    messageCenterService.save(villageCadres.getId(), villageCadres.getDistrictId(),
                            "[村书记信息]经过系统审核，" + villageCadres.getCadrePosition().getName() +
                                    villageCadres.getName() + "定级为" + checkRes.getName() + "！");
                } else {
                    actual.setName(null);
                    villageCadres.setQuasiAssessmentRank(null);
                    messageCenterService.save(villageCadres.getId(), villageCadres.getDistrictId(),
                            "[村书记信息]经过系统审核，" + villageCadres.getCadrePosition().getName() +
                                    villageCadres.getName() + "不符合任意一级专职村书记！");
                }
                actual.setCadresId(id);
                actual.setIsStandard("0");
                actual.setDistrictId(villageCadres.getDistrictId());
                ratingStandardService.deleteAllByCadresId(id);
                ratingStandardService.save(actual);
            }
        } else {
            integer--;
            checkMsg = "审核驳回意见:";
        }
        villageCadres.setState(integer.toString());
        villageCadres = villageCadresRepository.save(villageCadres);

        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setStatus(villageCadres.getState());
        informationAudit.setVillageId(id);
        informationAudit.setAuditAdvice(informationAuditDTO2.getAuditAdvice());
        informationAudit.setAuditor(informationAuditDTO2.getAuditor());

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
        ratingStandard.setAbilityJudgement(villageCadres.getEvaluation());
        // 上年度专职村书记考核等次
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

}