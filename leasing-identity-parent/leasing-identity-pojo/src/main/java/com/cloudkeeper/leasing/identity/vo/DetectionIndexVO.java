package com.cloudkeeper.leasing.identity.vo;

import com.cloudkeeper.leasing.base.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 监测指标 VO
 * @author asher
 */
@ApiModel(value = "监测指标 VO", description = "监测指标 VO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DetectionIndexVO extends BaseVO {

    /** 本年度村集体经济收入 */
    @ApiModelProperty(value = "本年度村集体经济收入", position = 10, required = true)
    private String currentYearEconomicIncome;

    /** 上年度村集体经济收入 */
    @ApiModelProperty(value = "上年度村集体经济收入", position = 10, required = true)
    private String lastYearEconomicIncome;

    /** 年度村集体经济收入佐证 */
    @ApiModelProperty(value = "年度村集体经济收入佐证", position = 10, required = true)
    private String incomeSupportDoc;

    /** 党建系统组织生活任务完成率 */
    @ApiModelProperty(value = "党建系统组织生活任务完成率", position = 10, required = true)
    private String partyActivityFinishRatio;

    /** 村干部员额数量 */
    @ApiModelProperty(value = "村干部员额数量", position = 10, required = true)
    private String cadrePosts;

    /** 村干部35周岁以下 */
    @ApiModelProperty(value = "村干部35周岁以下", position = 10, required = true)
    private String cadreBelowThirtyFive;

    /** 村干部35周岁到50周岁 */
    @ApiModelProperty(value = "村干部35周岁到50周岁", position = 10, required = true)
    private String cadreBetweenThirtyFiveToFifty;

    /** 村干部超过50周岁 */
    @ApiModelProperty(value = "村干部超过50周岁", position = 10, required = true)
    private String cadreOverFifty;

    /** 挂牌清理完成率 */
    @ApiModelProperty(value = "挂牌清理完成率", position = 10, required = true)
    private String cleanFinishRatio;

    /** 村级信访量 */
    @ApiModelProperty(value = "村级信访量", position = 10, required = true)
    private String petitionLetterAmount;

    /** 越级信访量 */
    @ApiModelProperty(value = "越级信访量", position = 10, required = true)
    private String skipPetitionLetterAmount;

    /** 信访化解量 */
    @ApiModelProperty(value = "信访化解量", position = 10, required = true)
    private String defusePetitionLetterAmount;

    /** 常住人口 */
    @ApiModelProperty(value = "常住人口", position = 10, required = true)
    private String population;

    /** 信访佐证材料 */
    @ApiModelProperty(value = "信访佐证材料", position = 10, required = true)
    private String petitionLetterSupportDoc;

    /** 为民实事工程总额 */
    @ApiModelProperty(value = "为民实事工程总额", position = 10, required = true)
    private String projectAmount;

    /** 总额佐证材料 */
    @ApiModelProperty(value = "总额佐证材料", position = 10, required = true)
    private String projectAmountSupportDoc;

    /** 人居环境是否通过抽查 */
    @ApiModelProperty(value = "人居环境是否通过抽查", position = 10, required = true)
    private String hasPass;

    /** 人居环境均分 */
    @ApiModelProperty(value = "人居环境均分", position = 10, required = true)
    private String avgScore;

    /** 人居佐证 */
    @ApiModelProperty(value = "人居佐证", position = 10, required = true)
    private String environmentSupportDoc;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 10, required = true)
    private String districtId;

    /** 任务id */
    @ApiModelProperty(value = "任务id", position = 10, required = true)
    private String taskId;

    /** 组织id */
    @ApiModelProperty(value = "组织名称", position = 10, required = true)
    private String districtName;

    /** 任务id */
    @ApiModelProperty(value = "任务名称", position = 10, required = true)
    private String taskName;

    /** 素能评价附件佐证材料 */
    @ApiModelProperty(value = "素能评价附件佐证材料", position = 10, required = true)
    private String judgeSupportDoc;

    /** 村集体经济收入（全口径） */
    @ApiModelProperty(value = "村集体经济收入（全口径）", position = 10, required = true)
    private String economicIncome;
}