package com.cloudkeeper.leasing.identity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 村干部管理 VO
 * @author cqh
 */
@ApiModel(value = "村干部管理 VO", description = "村干部管理 VO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VillageCadresStatisticsVO implements Serializable {
    @ApiModelProperty(value = "主键id", position = 1)
    private String id;
    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10, required = true)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 10, required = true)
    private String sex;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 10, required = true)
    private String nation;

    /** 健康状况 */
    @ApiModelProperty(value = "健康状况", position = 10, required = true)
    private String health;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 10, required = true)
    private LocalDate birth;

    /** 姓入党时间 */
    @ApiModelProperty(value = "姓入党时间", position = 10, required = true)
    private LocalDate partyTime;

    /** 工作时间 */
    @ApiModelProperty(value = "工作时间", position = 10, required = true)
    private LocalDate workTime;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 10, required = true)
    private String nativePlace;

    /** 出生地 */
    @ApiModelProperty(value = "出生地", position = 10, required = true)
    private String birthPlace;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 10, required = true)
    private String education;

    /** 身份证 */
    @ApiModelProperty(value = "身份证", position = 10, required = true)
    private String identityCard;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10, required = true)
    private String contact;

    /** 任职经历 */
    @ApiModelProperty(value = "任职经历", position = 10, required = true)
    private String postExperience;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 10, required = true)
    private String post;

    /** 所属组织 */
    @ApiModelProperty(value = "所属组织", position = 10, required = true)
    private String districtId;

    /** 所属组织名称 */
    @ApiModelProperty(value = "所属组织名称", position = 10, required = true)
    private String districtName;

    /** 所属镇级组织名称 */
    @ApiModelProperty(value = "所属组织名称", position = 10, required = true)
    private String parentDistrictName;

    @ApiModelProperty(value = "职称", position = 15)
    private String trainingTitle;

    @ApiModelProperty(value = "类型", position = 17)
    private String type;

    @ApiModelProperty(value = "财政负担类型", position = 19)
    private String financialType;

    /** 所属镇组织 */
    @ApiModelProperty(value = "所属镇组织", position = 21, required = true)
    private String parentDistrictId;

    @ApiModelProperty(value = "担任村书记时长", position = 19)
    private String onDutyTime;

    @ApiModelProperty(value = "素能评价", position = 19)
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

    @ApiModelProperty(value = "职位名称", position = 10)
    private String postName;

    private String auditor;

    private String auditAdvice;

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

    private List<HonourInfoVO> honours;

    private List<RewardInfoVO> rewards;

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
    @ApiModelProperty(value = "培训情况", position = 19)
    private List<TrainingInfoVO> trainingInfoVOS;

    @ApiModelProperty(value = "家庭情况", position = 19)
    private List<FamilyInfoVO> familyInfoVOS;

    @ApiModelProperty(value = "家庭成员工作情况", position = 19)
    private List<FamilyWorkInfoVO> familyWorkInfoVOS;
    @ApiModelProperty(value = "区分是否专职书记", position = 19)
    private String cadresType;

    @ApiModelProperty(value = "是否离退休", position = 19)
    private String hasRetire;
    /** 2020-03-11新增字段-----------------------结束--------------------------------**/

}
