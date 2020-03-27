package com.cloudkeeper.leasing.identity.domain;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 监测指标
 * @author asher
 */
@ApiModel(value = "监测指标", description = "监测指标")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Detection_Index")
public class DetectionIndex extends BaseEntity {

    /** 本年度村集体经济收入 */
    @ApiModelProperty(value = "本年度村集体经济收入", position = 10, required = true)
    @Column(length = 60)
    private String currentYearEconomicIncome;

    /** 上年度村集体经济收入 */
    @ApiModelProperty(value = "上年度村集体经济收入", position = 10, required = true)
    @Column(length = 60)
    private String lastYearEconomicIncome;

    /** 年度村集体经济收入佐证 */
    @ApiModelProperty(value = "年度村集体经济收入佐证", position = 10, required = true)
    @Column(length = 60)
    private String incomeSupportDoc;

    /** 党建系统组织生活任务完成率 */
    @ApiModelProperty(value = "党建系统组织生活任务完成率", position = 10, required = true)
    @Column(length = 60)
    private String partyActivityFinishRatio;

    /** 村干部员额数量 */
    @ApiModelProperty(value = "村干部员额数量", position = 10, required = true)
    @Column(length = 60)
    private String cadrePosts;

    /** 村干部35周岁以下 */
    @ApiModelProperty(value = "村干部35周岁以下", position = 10, required = true)
    @Column(length = 60)
    private String cadreBelowThirtyFive;

    /** 村干部35周岁到50周岁 */
    @ApiModelProperty(value = "村干部35周岁到50周岁", position = 10, required = true)
    @Column(length = 60)
    private String cadreBetweenThirtyFiveToFifty;

    /** 村干部超过50周岁 */
    @ApiModelProperty(value = "村干部超过50周岁", position = 10, required = true)
    @Column(length = 60)
    private String cadreOverFifty;

    /** 挂牌清理完成率 */
    @ApiModelProperty(value = "挂牌清理完成率", position = 10, required = true)
    @Column(length = 60)
    private String cleanFinishRatio;

    /** 村级信访量 */
    @ApiModelProperty(value = "村级信访量", position = 10, required = true)
    @Column(length = 60)
    private String petitionLetterAmount;

    /** 越级信访量 */
    @ApiModelProperty(value = "越级信访量", position = 10, required = true)
    @Column(length = 60)
    private String skipPetitionLetterAmount;

    /** 信访化解量 */
    @ApiModelProperty(value = "信访化解量", position = 10, required = true)
    @Column(length = 60)
    private String defusePetitionLetterAmount;

    /** 常住人口 */
    @ApiModelProperty(value = "常住人口", position = 10, required = true)
    @Column(length = 60)
    private String population;

    /** 信访佐证材料 */
    @ApiModelProperty(value = "信访佐证材料", position = 10, required = true)
    @Column(length = 60)
    private String petitionLetterSupportDoc;

    /** 为民实事工程总额 */
    @ApiModelProperty(value = "为民实事工程总额", position = 10, required = true)
    @Column(length = 60)
    private String projectAmount;

    /** 总额佐证材料 */
    @ApiModelProperty(value = "总额佐证材料", position = 10, required = true)
    @Column(length = 60)
    private String projectAmountSupportDoc;

    /** 人居环境是否通过抽查 */
    @ApiModelProperty(value = "人居环境是否通过抽查", position = 10, required = true)
    @Column(length = 60)
    private String hasPass;

    /** 人居环境均分 */
    @ApiModelProperty(value = "人居环境均分", position = 10, required = true)
    @Column(length = 60)
    private String avgScore;

    /** 人居佐证 */
    @ApiModelProperty(value = "人居佐证", position = 10, required = true)
    @Column(length = 60)
    private String environmentSupportDoc;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    @Column(length = 60)
    private String districtId;

    /** 任务id */
    @ApiModelProperty(value = "任务id", position = 10, required = true)
    @Column(length = 60)
    private String taskId;

    /** 组织id */
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    @Column(length = 60)
    private String districtName;

    /** 任务id */
    @ApiModelProperty(value = "任务名称", position = 10, required = true)
    @Column(length = 60)
    private String taskName;

    /** 素能评价附件佐证材料 */
    @ApiModelProperty(value = "素能评价附件佐证材料", position = 10, required = true)
    private String judgeSupportDoc;

}