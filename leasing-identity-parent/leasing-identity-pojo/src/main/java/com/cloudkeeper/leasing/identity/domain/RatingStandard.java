package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.base.utils.TimerUtil;
import com.cloudkeeper.leasing.identity.vo.RatingStandardVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.text.ParseException;

/**
 * 村书记评级标准
 * @author asher
 */
@ApiModel(value = "村书记评级标准", description = "村书记评级标准")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rating_standard")
public class RatingStandard extends BaseEntity {

    /** 标椎名称 */
    @ApiModelProperty(value = "标椎名称", position = 10, required = true)
    @Column(length = 60)
    private String name;


    /** 担任村书记时长 */
    @ApiModelProperty(value = "担任村书记时长", position = 10, required = true)
    @Column(length = 60)
    private String workDuration = "0";

    /** 能力研判 */
    @ApiModelProperty(value = "能力研判", position = 10, required = true)
    @Column(length = 60)
    private String abilityJudgement = "0";

    /** 上年度专职村书记考核等次 */
    @ApiModelProperty(value = "上年度专职村书记考核等次", position = 10, required = true)
    @Column(length = 60)
    private String lastGrade = "0";

    /** 年度考核获得“称职”及以上等次的次数 */
    @ApiModelProperty(value = "年度考核获得“称职”及以上等次的次数", position = 10, required = true)
    @Column(length = 60)
    private String gradeTimes = "0";

    /** 连续几年年度考核获得“称职”及以上等次 */
    @ApiModelProperty(value = "连续几年年度考核获得“称职”及以上等次", position = 10, required = true)
    @Column(length = 60)
    private String gradeLastTimes = "0";

    /** 连续几年年度考核获得“优秀“等次 */
    @ApiModelProperty(value = "连续几年年度考核获得“优秀“等次", position = 10, required = true)
    @Column(length = 60)
    private String aGradeLastTimes = "0";

    /** 年度考核获得“优秀”等次的次数 */
    @ApiModelProperty(value = "年度考核获得“优秀”等次的次数", position = 10, required = true)
    @Column(length = 60)
    private String aGradeTimes = "0";

    /** 连续几次年度考核获得前10 */
    @ApiModelProperty(value = "连续几次年度考核获得前10", position = 10, required = true)
    @Column(length = 60)
    private String topTenGradeLastTimes = "0";

    /** 连续几次年度考核获得前5 */
    @ApiModelProperty(value = "连续几次年度考核获得前5", position = 10, required = true)
    @Column(length = 60)
    private String topFiveGradeLastTimes = "0";

    /** 连续几次年度考核获得前3 */
    @ApiModelProperty(value = "连续几次年度考核获得前3", position = 10, required = true)
    @Column(length = 60)
    private String topThreeGradeLastTimes = "0";

    /** 获得表彰类型 */
    @ApiModelProperty(value = "获得表彰类型", position = 10, required = true)
    @Column(length = 60)
    private String honoursType = "";

    /** 村书记 */
    @ApiModelProperty(value = "村书记", position = 10, required = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId", insertable = false, updatable = false)
    private VillageCadres villageCadres;

    /** 村书记id */
    @ApiModelProperty(value = "村书记id", position = 10, required = true)
    @Column(length = 60)
    private String cadresId;

    /** 村id */
    @ApiModelProperty(value = "村id", position = 10, required = true)
    private String districtId;

    /** 是否为标准 */
    @ApiModelProperty(value = "是否为标准", position = 10, required = true)
    @Column(length = 60)
    private String isStandard;

    /** 目的村书记等级 */
    @ApiModelProperty(value = "目的村书记等级", position = 10, required = true)
    private String purpose;

    /** 当前评级状态 */
    @ApiModelProperty(value = "当前评级状态", position = 10, required = true)
    private String status;

    /** 标准整数值 */
    @ApiModelProperty(value = "标准整数值", position = 10, required = true)
    private Integer standardValue;

    /** 是否可晋升 */
    @ApiModelProperty(value = "是否可晋升", position = 10, required = true)
    private String promotable;

    /** 是否有效 */
    @ApiModelProperty(value = "是否有效", position = 10, required = true)
    private String enable;

    @Nonnull
    @Override
    public <T> T convert(@Nonnull Class<T> clazz) {
        T convert = super.convert(clazz);
        RatingStandardVO ratingStandardVO = (RatingStandardVO) convert;
        if (!StringUtils.isEmpty(villageCadres)) {
            ratingStandardVO.setCadresName(villageCadres.getName());
            try {
                ratingStandardVO.setAge(String.valueOf(TimerUtil.getAgeByBirth(villageCadres.getBirth())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ratingStandardVO.setEducation(villageCadres.getEducation());
            ratingStandardVO.setVillageName(villageCadres.getDistrictName());
            ratingStandardVO.setTownName(villageCadres.getParentDistrictName());
            ratingStandardVO.setCurrentLevel(villageCadres.getQuasiAssessmentRank());
            ratingStandardVO.setCurrentLevelJudgeTime(villageCadres.getTermOfOffice());
        }
        return (T)ratingStandardVO;
    }
}