package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.identity.vo.HonourInfoVO;
import com.cloudkeeper.leasing.identity.vo.RewardInfoVO;
import com.cloudkeeper.leasing.identity.vo.VillageCadresVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 村干部管理
 * @author cqh
 */
@ApiModel(value = "村干部管理", description = "村干部管理")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "village_cadres")
public class VillageCadres extends BaseEntity {

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10, required = true)
    @Column(length = 60)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 10, required = true)
    @Column(length = 60)
    private String sex;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 10, required = true)
    @Column(length = 60)
    private String nation;

    /** 健康状况 */
    @ApiModelProperty(value = "健康状况", position = 10, required = true)
    @Column(length = 60)
    private String health;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 10, required = true)
    @Column(length = 60)
    private LocalDate birth;

    /** 入党时间 */
    @ApiModelProperty(value = "入党时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate partyTime;

    /** 工作时间 */
    @ApiModelProperty(value = "工作时间", position = 10, required = true)
    @Column(length = 60)
    private LocalDate workTime;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 10, required = true)
    @Column(length = 60)
    private String nativePlace;

    /** 出生地 */
    @ApiModelProperty(value = "出生地", position = 10, required = true)
    @Column(length = 60)
    private String birthPlace;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 10, required = true)
    @Column(length = 60)
    private String education;

    /** 身份证 */
    @ApiModelProperty(value = "身份证", position = 10, required = true)
    @Column(length = 60)
    private String identityCard;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10, required = true)
    @Column(length = 60)
    private String contact;

    /** 任职经历 */
    @ApiModelProperty(value = "任职经历", position = 10, required = true)
    @Column(length = 60)
    private String postExperience;

    /** 所属组织 */
    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 所属镇组织 */
    @ApiModelProperty(value = "所属镇组织", position = 10, required = true)
    @Column(length = 60)
    private String parentDistrictId;

    /** 组织 */
    @ApiModelProperty(value = "组织", position = 24)
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict sysDistrict;

    /** 镇级组织 */
    @ApiModelProperty(value = "组织", position = 24)
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parentDistrictId",referencedColumnName = "districtId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDistrict parentSysDistrict;

    /** 岗位 */
    @ApiModelProperty(value = "岗位", position = 13)
    @OneToOne(mappedBy = "villageCadres", cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @NotFound(action = NotFoundAction.IGNORE)
    private CadrePosition cadrePosition;

    @ApiModelProperty(value = "职称", position = 15)
    private String trainingTitle;

    @ApiModelProperty(value = "类型", position = 17)
    private String type;

    @ApiModelProperty(value = "财政负担类型", position = 19)
    private String financialType;

    @ApiModelProperty(value = "职责", position = 19)
    private String duty;

    @ApiModelProperty(value = "担任村书记时长", position = 19)
    private String onDutyTime;

    @ApiModelProperty(value = "能力研判（原素能评价字段）", position = 19)
    private String evaluation;

    @ApiModelProperty(value = "专业职称", position = 19)
    private String professionalTitle;

    @ApiModelProperty(value = "工作单位及职务", position = 19)
    private String workPlaceAndDuty;

    @ApiModelProperty(value = "拟评定职级", position = 19)
    private String quasiAssessmentRank;

    @ApiModelProperty(value = "工作简历", position = 19)
    private String workExperience;

    @ApiModelProperty(value = "曾受综合表彰情况", position = 19)
    private String comprehensiveCommendation;

    @ApiModelProperty(value = "年度考核情况", position = 19)
    private String annualAssessment;

    @ApiModelProperty(value = "状态", position = 19)
    private String state;

    @ApiModelProperty(value = "本年度基本报酬", position = 19)
    private String currentBaseSalary;

    @ApiModelProperty(value = "上年度基本报酬", position = 19)
    private String lastBaseSalary;

    @ApiModelProperty(value = "上年度考核报酬", position = 19)
    private String lastCheckSalary;

    @ApiModelProperty(value = "上年度增收奖励", position = 19)
    private String lastIncreaseBonus;

    @ApiModelProperty(value = "头像", position = 19)
    private String headSculpture;

    @ApiModelProperty(value = "纸质文档", position = 19)
    private String paperDocument;

    @ApiModelProperty(value = "职级等次", position = 19)
    private String rank;

    @ApiModelProperty(value = "入额时间", position = 19)
    private LocalDate entryAmountTime;

    @OneToMany(mappedBy = "villageCadres")
    @OrderBy("createdAt desc")
    private List<InformationAudit> informationAudits = new ArrayList<>();

    @ApiModelProperty(value = "日常工作情况", position = 19)
    private String commonWork;

    @ApiModelProperty(value = "实际考核", position = 19)
    private String actualReview;

    @ApiModelProperty(value = "素能评价", position = 19)
    private String primeOpinion;

    @ApiModelProperty(value = "民主评价", position = 19)
    private String democraticOpinion;

    @ApiModelProperty(value = "加减分情况", position = 19)
    private String additionSubtractionOpinion;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId")
    private List<HonourInfo> honourInfos;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId")
    private List<RewardInfo> rewardInfos;

    /** 2020-03-11新增字段-----------------------开始--------------------------------**/
    @ApiModelProperty(value = "人员类型", position = 19)
    private String personnelType;

    @ApiModelProperty(value = "身份证号", position = 19)
    private String IDcardNumber;

    @ApiModelProperty(value = "驾驶证号", position = 19)
    private String drivingLicenseNumber;

    @ApiModelProperty(value = "信用状况", position = 19)
    private String creditStatus;

    @ApiModelProperty(value = "任现职级时间", position = 19)
    private String termOfOffice;

    @ApiModelProperty(value = "上年度新型农村合作医疗购买人数", position = 19)
    private String medicalInsuranceNumber;
    @ApiModelProperty(value = "村委会月用电量", position = 19)
    private String monthlyElectricity;
    @ApiModelProperty(value = "农村产权交易平台交易数量", position = 19)
    private String propertyRightsTransactionsNumber;
    @ApiModelProperty(value = "12345热线月投诉量", position = 19)
    private String complaintVolume;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId")
    private List<TrainingInfo> trainingInfos;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId")
    @ApiModelProperty(value = "家庭情况", position = 19)
    private List<FamilyInfo> familyInfos;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId")
    @ApiModelProperty(value = "家庭成员工作情况", position = 19)
    private List<FamilyWorkInfo> familyWorkInfos;
    @ApiModelProperty(value = "区分是否专职书记", position = 19)
    private String cadresType;
    /** 2020-03-11新增字段-----------------------结束--------------------------------**/

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        VillageCadresVO villageCadresVO = (VillageCadresVO) convert;
        if (!StringUtils.isEmpty(this.cadrePosition)){
            villageCadresVO.setPost(this.cadrePosition.getId());
            villageCadresVO.setPostName(this.cadrePosition.getName());
        }
        if (!StringUtils.isEmpty(this.sysDistrict)){
            villageCadresVO.setDistrictName(this.sysDistrict.getDistrictName());
        }
        if (!StringUtils.isEmpty(this.parentSysDistrict)) {
            villageCadresVO.setParentDistrictName(this.parentSysDistrict.getDistrictName());
        }
        if (informationAudits.size() > 0) {
            InformationAudit first = informationAudits.get(0);
            villageCadresVO.setAuditor(first.getAuditor());
            villageCadresVO.setAuditAdvice(first.getAuditAdvice());
        }
        if (!StringUtils.isEmpty(this.honourInfos)) {
            villageCadresVO.setHonours(HonourInfo.convert(this.honourInfos, HonourInfoVO.class));
        }

        if (!StringUtils.isEmpty(this.rewardInfos)) {
            villageCadresVO.setRewards(RewardInfo.convert(this.rewardInfos, RewardInfoVO.class));
        }

        return (T) villageCadresVO;
    }

    @Nonnull
    @Override
    public <T> T pageConvert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        VillageCadresVO villageCadresVO = (VillageCadresVO) convert;
        if (!StringUtils.isEmpty(this.cadrePosition)){
            villageCadresVO.setPost(this.cadrePosition.getId());
            villageCadresVO.setPostName(this.cadrePosition.getName());
        }
        if (!StringUtils.isEmpty(this.sysDistrict)){
            villageCadresVO.setDistrictName(this.sysDistrict.getDistrictName());
        }
        if (!StringUtils.isEmpty(this.parentSysDistrict)) {
            villageCadresVO.setParentDistrictName(this.parentSysDistrict.getDistrictName());
        }
        if (informationAudits.size() > 0) {
            InformationAudit first = informationAudits.get(0);
            villageCadresVO.setAuditor(first.getAuditor());
            villageCadresVO.setAuditAdvice(first.getAuditAdvice());
        }
        return (T) villageCadresVO;
    }
}
