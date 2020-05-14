package com.cloudkeeper.leasing.identity.dto.kpivillagequota;

import com.cloudkeeper.leasing.identity.dto.kpievaluation.KPIEvaluationDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 村考核指标 打分DTO
 * @author yujian
 */
@ApiModel(value = "村考核指标 打分DTO", description = "村考核指标 打分DTO")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScoringDTO {

    // villageQuotaId
    private String id;

    // 指标得分
    private String score;

    // 权重
    private String weight;

    /** 本年度村集体经济收入 */
    private String currentYearEconomicIncome;

    /** 上年度村集体经济收入 */
    private String lastYearEconomicIncome;

    /** 年度村集体经济收入佐证 */
    private String incomeSupportDoc;

    /** 党建系统组织生活任务完成率 */
    private String partyActivityFinishRatio;

    /** 村干部员额数量 */
    private String cadrePosts;

    /** 村干部35周岁以下 */
    private String cadreBelowThirtyFive;

    /** 村干部35周岁到50周岁 */
    private String cadreBetweenThirtyFiveToFifty;

    /** 村干部超过50周岁 */
    private String cadreOverFifty;

    /** 挂牌清理完成率 */
    private String cleanFinishRatio;

    /** 村级信访量 */
    private String petitionLetterAmount;

    /** 越级信访量 */
    private String skipPetitionLetterAmount;

    /** 信访化解量 */
    private String defusePetitionLetterAmount;

    /** 常住人口 */
    private String population;

    /** 信访佐证材料 */
    private String petitionLetterSupportDoc;

    /** 为民实事工程总额 */
    private String projectAmount;

    /** 总额佐证材料 */
    private String projectAmountSupportDoc;

    /** 人居环境是否通过抽查 */
    private String hasPass;

    /** 人居环境均分 */
    private String avgScore;

    /** 人居佐证 */
    private String environmentSupportDoc;

    /** 组织id */
    private String districtId;

    /** 任务id */
    private String taskId;

    /** 组织id */
    private String districtName;

    /** 任务id */
    private String taskName;

    /** 素能评价附件佐证材料 */
    private String judgeSupportDoc;

    private List<KPIEvaluationDTO> kpiEvaluations;
}
