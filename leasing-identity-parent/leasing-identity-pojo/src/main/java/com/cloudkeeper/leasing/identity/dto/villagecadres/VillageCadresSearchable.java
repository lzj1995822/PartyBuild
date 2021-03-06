package com.cloudkeeper.leasing.identity.dto.villagecadres;

import com.cloudkeeper.leasing.base.dto.BaseSearchable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * 村干部管理 查询DTO
 * @author cqh
 */
@ApiModel(value = "村干部管理 查询DTO", description = "村干部管理 查询DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VillageCadresSearchable extends BaseSearchable {

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

    @ApiModelProperty(value = "担任村书记时长", position = 19)
    private String onDutyTime;
    @ApiModelProperty(value = "担任村书记时长-月", position = 19)
    private String onDutyMonth;
    @ApiModelProperty(value = "素能评价", position = 19)
    private String evalution;

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
    @ApiModelProperty(value = "是否离退休", position = 19)
    private String hasRetire;
    private String cadresType;
    /** 2020-03-11新增字段-----------------------结束--------------------------------**/
    private String age;

    private String enterPartyTime;

    private List<String> export;

    @ApiModelProperty(value = "驾驶证号证材料", position = 10, required = true)
    private String drivingLicenseNumberAnnex;
    @ApiModelProperty(value = "曾受综合表彰情况证材料", position = 10, required = true)
    private String comprehensiveCommendationAnnex;
    @ApiModelProperty(value = "专业职称证材料", position = 10, required = true)
    private String professionalTitleAnnex;
    @ApiModelProperty(value = "职称证材料", position = 10, required = true)
    private String trainingTitleAnnex;
    @ApiModelProperty(value = "任职经历证材料", position = 10, required = true)
    private String postExperienceAnnex;
    @ApiModelProperty(value = "身份证材料", position = 10, required = true)
    private String identityCardAnnex;
    @ApiModelProperty(value = "学历证明材料", position = 10, required = true)
    private String educationAnnex;
    @ApiModelProperty(value = "工作时间证明材料", position = 10, required = true)
    private String workTimeAnnex;
    @ApiModelProperty(value = "入党时间证明材料", position = 10, required = true)
    private String partyTimeAnnex;
    @ApiModelProperty(value = "任现职级时间证材料", position = 10, required = true)
    private String termOfOfficeAnnex;
    @ApiModelProperty(value = "任命文件材料", position = 10, required = true)
    private String appointmentAnnex;

    @ApiModelProperty(value = "全日制学历", position = 10, required = true)
    private String fullTimeEdu;

    @ApiModelProperty(value = "非全日制学历", position = 10, required = true)
    private String partTimeEdu;

    @ApiModelProperty(value = "全日制学校", position = 10, required = true)
    private String fullTimeSchool;

    @ApiModelProperty(value = "非全日制学校", position = 10, required = true)
    private String partTimeSchool;

    @ApiModelProperty(value = "村干部职务", position = 10, required = true)
    private String cadresPost;

    @ApiModelProperty(value = "政治面貌", position = 10, required = true)
    private String politicalOutlook;

}
