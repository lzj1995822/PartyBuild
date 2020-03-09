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
    private String workDuration;

    /** 能力研判 */
    @ApiModelProperty(value = "能力研判", position = 10, required = true)
    @Column(length = 60)
    private String abilityJudgement;

    /** 上年度专职村书记考核等次 */
    @ApiModelProperty(value = "上年度专职村书记考核等次", position = 10, required = true)
    @Column(length = 60)
    private String lastGrade;

    /** 年度考核获得“称职”及以上等次的次数 */
    @ApiModelProperty(value = "年度考核获得“称职”及以上等次的次数", position = 10, required = true)
    @Column(length = 60)
    private String gradeTimes;

    /** 连续几年年度考核获得“称职”及以上等次 */
    @ApiModelProperty(value = "连续几年年度考核获得“称职”及以上等次", position = 10, required = true)
    @Column(length = 60)
    private String gradeLastTimes;

    /** 连续几年年度考核获得“优秀“等次 */
    @ApiModelProperty(value = "连续几年年度考核获得“优秀“等次", position = 10, required = true)
    @Column(length = 60)
    private String aGradeLastTimes;

    /** 年度考核获得“优秀”等次的次数 */
    @ApiModelProperty(value = "年度考核获得“优秀”等次的次数", position = 10, required = true)
    @Column(length = 60)
    private String aGradeTimes;

    /** 连续几次年度考核获得前10 */
    @ApiModelProperty(value = "连续几次年度考核获得前10", position = 10, required = true)
    @Column(length = 60)
    private String topTenGradeLastTimes;

    /** 连续几次年度考核获得前5 */
    @ApiModelProperty(value = "连续几次年度考核获得前5", position = 10, required = true)
    @Column(length = 60)
    private String topFiveGradeLastTimes;

    /** 连续几次年度考核获得前3 */
    @ApiModelProperty(value = "连续几次年度考核获得前3", position = 10, required = true)
    @Column(length = 60)
    private String topThreeGradeLastTimes;

    /** 获得表彰类型 */
    @ApiModelProperty(value = "获得表彰类型", position = 10, required = true)
    @Column(length = 60)
    private String honoursType;

    /** 村书记 */
    @ApiModelProperty(value = "村书记", position = 10, required = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadresId", insertable = false, updatable = false)
    private VillageCadres villageCadres;

    /** 村书记id */
    @ApiModelProperty(value = "村书记id", position = 10, required = true)
    @Column(length = 60)
    private String cadresId;

    /** 是否为标准 */
    @ApiModelProperty(value = "是否为标准", position = 10, required = true)
    @Column(length = 60)
    private String isStandard;

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
            ratingStandardVO.setVillageName(villageCadres.getSysDistrict().getDistrictName());
            ratingStandardVO.setTownName(villageCadres.getParentSysDistrict().getDistrictName());
        }
        return (T)ratingStandardVO;
    }
}