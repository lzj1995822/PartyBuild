package com.cloudkeeper.leasing.identity.dto.ratingstandard;

import com.cloudkeeper.leasing.base.dto.BaseEditDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 村书记评级标准 DTO
 * @author asher
 */
@ApiModel(value = "村书记评级标准 DTO", description = "村书记评级标准 DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RatingStandardDTO extends BaseEditDTO {

    /** 标准等级 */
    @ApiModelProperty(value = "标准等级", position = 10, required = true)
    private String name;

    /** 担任村书记时长 */
    @ApiModelProperty(value = "担任村书记时长", position = 10, required = true)
    private String workDuration;

    /** 能力研判 */
    @ApiModelProperty(value = "能力研判", position = 10, required = true)
    private String abilityJudgement;

    /** 上年度专职村书记考核等次 */
    @ApiModelProperty(value = "上年度专职村书记考核等次", position = 10, required = true)
    private String lastGrade;

    /** 年度考核获得“称职”及以上等次的次数 */
    @ApiModelProperty(value = "年度考核获得“称职”及以上等次的次数", position = 10, required = true)
    private String gradeTimes;

    /** 连续几年年度考核获得“称职”及以上等次 */
    @ApiModelProperty(value = "连续几年年度考核获得“称职”及以上等次", position = 10, required = true)
    private String gradeLastTimes;

    /** 连续几年年度考核获得“优秀“等次 */
    @ApiModelProperty(value = "连续几年年度考核获得“优秀“等次", position = 10, required = true)
    private String aGradeLastTimes;

    /** 年度考核获得“优秀”等次的次数 */
    @ApiModelProperty(value = "年度考核获得“优秀”等次的次数", position = 10, required = true)
    private String aGradeTimes;

    /** 连续几次年度考核获得前10 */
    @ApiModelProperty(value = "连续几次年度考核获得前10", position = 10, required = true)
    private String topTenGradeLastTimes;

    /** 连续几次年度考核获得前5 */
    @ApiModelProperty(value = "连续几次年度考核获得前5", position = 10, required = true)
    private String topFiveGradeLastTimes;

    /** 连续几次年度考核获得前3 */
    @ApiModelProperty(value = "连续几次年度考核获得前3", position = 10, required = true)
    private String topThreeGradeLastTimes;

    /** 获得表彰类型 */
    @ApiModelProperty(value = "获得表彰类型", position = 10, required = true)
    private String honoursType;


    /** 村书记id */
    @ApiModelProperty(value = "村书记id", position = 10, required = true)
    private String cadresId;

    /** 是否为标准 */
    @ApiModelProperty(value = "是否为标准", position = 10, required = true)
    private String isStandard;

    /** 村id */
    @ApiModelProperty(value = "村id", position = 10, required = true)
    private String districtId;

    /** 标准整数值 */
    @ApiModelProperty(value = "标准整数值", position = 10, required = true)
    private Integer standardValue;

    /** 是否可晋升 */
    @ApiModelProperty(value = "是否可晋升", position = 10, required = true)
    private String promotable;

    /** 是否有效 */
    @ApiModelProperty(value = "是否有效", position = 10, required = true)
    private String enable;

}