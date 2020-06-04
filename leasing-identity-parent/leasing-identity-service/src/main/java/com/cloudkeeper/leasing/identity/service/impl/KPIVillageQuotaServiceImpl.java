package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.DetectionIndex;
import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.identity.dto.detectionindex.DetectionIndexSearchable;
import com.cloudkeeper.leasing.identity.dto.kpievaluation.KPIEvaluationSearchable;
import com.cloudkeeper.leasing.identity.repository.KPIVillageQuotaRepository;
import com.cloudkeeper.leasing.identity.service.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 村考核指标 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIVillageQuotaServiceImpl extends BaseServiceImpl<KPIVillageQuota> implements KPIVillageQuotaService {

    /** 村考核指标 repository */
    private final KPIVillageQuotaRepository kPIVillageQuotaRepository;

    /* 二级指标service */
    private final KPITownQuotaService kpiTownQuotaService;

    //监测指标
    private final DetectionIndexService detectionIndexService;

    //综合评价
    private final KPIEvaluationService kpiEvaluationService;

    private final KPIAttachmentService kpiAttachmentService;

    @Override
    protected BaseRepository<KPIVillageQuota> getBaseRepository() {
        return kPIVillageQuotaRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("weight", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtName", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void deleteAllByParentDistrictIdAndParentQuotaId(String parentDistrictId, String parentQuotaId) {
        kPIVillageQuotaRepository.deleteAllByParentDistrictIdAndParentQuotaId(parentDistrictId,parentQuotaId);
    }

    @Override
    public List<Map<String, Object>> buildCommonWorkData(String districtId, String taskId, String reviewTaskId, String parentQuotaId, String quarter, String makeQuotaDistrictId) {
        List<KPITownQuota> townQuota = getKPITownQuota(districtId, makeQuotaDistrictId, taskId, parentQuotaId);
        if (townQuota.size() == 0) {
            return new ArrayList<>();
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageQuota.class);
        detachedCriteria.add(Restrictions.in("townQuotaId", townQuota.stream().map(KPITownQuota::getId).collect(Collectors.toList())));
        if (!StringUtils.isEmpty(quarter)) {
            detachedCriteria.add(Restrictions.eq("quarter", quarter));
        }
        detachedCriteria.add(Restrictions.eq("districtId", districtId));
        List<KPIVillageQuota> villageQuotas = findAll(detachedCriteria);
        Map<String, Map<String, String>> map = new HashMap<>();
        for (KPIVillageQuota item : villageQuotas) {
            String quotaName = item.getKpiTownQuota().getQuotaName();
            if (StringUtils.isEmpty(quotaName) || StringUtils.isEmpty(item.getQuarter())) {
                continue;
            }
            if (!map.containsKey(quotaName)) {
                map.put(quotaName, new HashMap<>());
            }
            Map<String, String> quotas = map.get(quotaName);
            quotas.put(item.getQuarter(), item.getScore());
        }

        ArrayList<KPITownQuota> thirdCollect = townQuota.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(KPITownQuota::getQuotaName))), ArrayList::new));
        Map<String, List<Map<String, Object>>> thirdRes = new HashMap<>();
        for (KPITownQuota item : thirdCollect) {
            String parentQuotaId1 = item.getParentQuotaId();
            if (!thirdRes.containsKey(parentQuotaId1)) {
                thirdRes.put(parentQuotaId1, new ArrayList<>());
            }
            Map<String, Object> val = new HashMap<>();
            val.put("quotasName", item.getQuotaName());
            val.put("quotaScore", item.getScore());
            val.put("quarters", map.get(item.getQuotaName()));
            thirdRes.get(parentQuotaId1).add(val);
        }

        ArrayList<KPITownQuota> secondCollect = thirdCollect.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(KPITownQuota::getParentQuotaId))), ArrayList::new));
        List<Map<String, Object>> secondRes = new ArrayList<>();
        for (KPITownQuota item : secondCollect) {
            Map<String, Object> val = new HashMap<>();
            val.put("quotasName", item.getParentQuotaName());
            val.put("quotaScore", item.getScore());
            val.put("attachment", kpiAttachmentService.findAllByQuota(item.getParentQuotaId(), item.getDistrictId()));
            val.put("thirdRes", thirdRes.get(item.getParentQuotaId()));
            secondRes.add(val);
        }

        return secondRes;
    }

    @Override
    public List<Map<String, Object>> buildCommonData(String districtId, String taskId, String reviewTaskId, String rootQuotaId, String makeQuotaDistrictId) {
        List<KPITownQuota> townQuota = getKPITownQuota(districtId, makeQuotaDistrictId, taskId, rootQuotaId);
        if (townQuota.size() == 0) {
            return new ArrayList<>();
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageQuota.class);
        detachedCriteria.add(Restrictions.in("townQuotaId", townQuota.stream().map(KPITownQuota::getId).collect(Collectors.toList())));
        detachedCriteria.add(Restrictions.eq("districtId", districtId));
        List<KPIVillageQuota> villageQuotas = findAll(detachedCriteria);
        Map<String, String> map = new HashMap<>();
        for (KPIVillageQuota item : villageQuotas) {
            map.put(item.getKpiTownQuota().getQuotaName(), item.getScore());
        }

        Map<String, List<Map<String, Object>>> thirdRes = new HashMap<>();
        for (KPITownQuota item : townQuota) {
            String parentQuotaId = item.getParentQuotaId();
            if (!thirdRes.containsKey(parentQuotaId)) {
                thirdRes.put(parentQuotaId, new ArrayList<>());
            }
            Map<String, Object> val = new HashMap<>();
            val.put("quotasName", item.getQuotaName());
            val.put("quotaScore", item.getScore());
            val.put("value", map.get(item.getQuotaName()));
            thirdRes.get(parentQuotaId).add(val);
        }

        ArrayList<KPITownQuota> secondCollect = townQuota.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(KPITownQuota::getParentQuotaId))), ArrayList::new));
        List<Map<String, Object>> secondRes = new ArrayList<>();
        for (KPITownQuota item : secondCollect) {
            Map<String, Object> val = new HashMap<>();
            val.put("quotasName", item.getParentQuotaName());
            val.put("quotaScore", item.getScore());
            val.put("attachment", kpiAttachmentService.findByQuota(item.getParentQuotaId(), item.getDistrictId(), null, reviewTaskId));
            val.put("thirdRes", thirdRes.get(item.getParentQuotaId()));
            secondRes.add(val);
        }

        return secondRes;
    }

    @Override
    public List<Map<String, Object>> buildWatchQuotaData(String districtId, String taskId, String reviewTaskId, String taskYear, String parentQuotaId, String makeQuotaDistrictId) {
        List<KPITownQuota> townQuota = getKPITownQuota(districtId, makeQuotaDistrictId, taskId, parentQuotaId);

        if (townQuota.size() == 0) {
            return new ArrayList<>();
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageQuota.class);
        detachedCriteria.add(Restrictions.in("townQuotaId", townQuota.stream().map(KPITownQuota::getId).collect(Collectors.toList())));
        detachedCriteria.add(Restrictions.eq("districtId", districtId));
        List<KPIVillageQuota> villageQuotas = findAll(detachedCriteria);
        Map<String, String> map = new HashMap<>();
        for (KPIVillageQuota item : villageQuotas) {
            map.put(item.getKpiTownQuota().getQuotaName(), item.getScore());
        }

        Map<String, List<Map<String, Object>>> thirdRes = new HashMap<>();
        for (KPITownQuota item : townQuota) {
            String parentQuotaId1 = item.getParentQuotaId();
            if (!thirdRes.containsKey(parentQuotaId1)) {
                thirdRes.put(parentQuotaId1, new ArrayList<>());
            }
            Map<String, Object> val = new HashMap<>();
            val.put("quotasName", item.getQuotaName());
            val.put("quotaScore", item.getScore());
            val.put("value", map.get(item.getQuotaName()));
            thirdRes.get(parentQuotaId1).add(val);
        }

        handleAttachInfo(thirdRes, taskYear + "0301", reviewTaskId, districtId);
        handleAttachInfo(thirdRes, taskYear + "0302", reviewTaskId, districtId);
        handleAttachInfo(thirdRes, taskYear + "0303", reviewTaskId, districtId);
        handleAttachInfo(thirdRes, taskYear + "0304", reviewTaskId, districtId);
        handleAttachInfo(thirdRes, taskYear + "0305", reviewTaskId, districtId);

        ArrayList<KPITownQuota> secondCollect = townQuota.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(KPITownQuota::getParentQuotaId))), ArrayList::new));
        List<Map<String, Object>> secondRes = new ArrayList<>();
        for (KPITownQuota item : secondCollect) {
            Map<String, Object> val = new HashMap<>();
            val.put("quotasName", item.getParentQuotaName());
            val.put("quotaScore", item.getScore());
            val.put("attachment", kpiAttachmentService.findByQuota(item.getParentQuotaId(), item.getDistrictId(), null, reviewTaskId));
            val.put("thirdRes", thirdRes.get(item.getParentQuotaId()));
            secondRes.add(val);
        }

        return secondRes;
    }

    @Override
    public List<Map<String, Object>> buildCommentQuotaData(String districtId, String taskId, String reviewTaskId,  String taskYear, String parentQuotaId, String makeQuotaDistrictId) {
        List<KPITownQuota> townQuota = getKPITownQuota(districtId, makeQuotaDistrictId, taskId, parentQuotaId);

        if (townQuota.size() == 0) {
            return new ArrayList<>();
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageQuota.class);
        detachedCriteria.add(Restrictions.in("townQuotaId", townQuota.stream().map(KPITownQuota::getId).collect(Collectors.toList())));
        detachedCriteria.add(Restrictions.eq("districtId", districtId));
        List<KPIVillageQuota> villageQuotas = findAll(detachedCriteria);
        Map<String, String> map = new HashMap<>();
        for (KPIVillageQuota item : villageQuotas) {
            map.put(item.getKpiTownQuota().getQuotaName(), item.getScore());
        }

        Map<String, List<Map<String, Object>>> thirdRes = new HashMap<>();
        for (KPITownQuota item : townQuota) {
            String parentQuotaId1 = item.getParentQuotaId();
            if (!thirdRes.containsKey(parentQuotaId1)) {
                thirdRes.put(parentQuotaId1, new ArrayList<>());
            }
            Map<String, Object> val = new HashMap<>();
            val.put("quotasName", item.getQuotaName());
            val.put("quotaScore", item.getScore());
            val.put("value", map.get(item.getQuotaName()));
            thirdRes.get(parentQuotaId1).add(val);
        }
        handleAttachInfo(thirdRes, taskYear +"0501", reviewTaskId, districtId);
        handleAttachInfo(thirdRes, taskYear + "0502", reviewTaskId, districtId);
        handleAttachInfo(thirdRes, taskYear + "0503", reviewTaskId, districtId);

        ArrayList<KPITownQuota> secondCollect = townQuota.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(KPITownQuota::getParentQuotaId))), ArrayList::new));
        List<Map<String, Object>> secondRes = new ArrayList<>();
        for (KPITownQuota item : secondCollect) {
            Map<String, Object> val = new HashMap<>();
            val.put("quotasName", item.getParentQuotaName());
            val.put("quotaScore", item.getScore());
            val.put("attachment", kpiAttachmentService.findByQuota(item.getParentQuotaId(), item.getDistrictId(),  null, reviewTaskId));
            val.put("thirdRes", thirdRes.get(item.getParentQuotaId()));
            secondRes.add(val);
        }

        return secondRes;
    }

    @Override
    public void deleteAllByTownQuotaId(String townQuotaId) {
        kPIVillageQuotaRepository.deleteAllByTownQuotaId(townQuotaId);
    }

    @Override
    public void deleteAllByTownQuotaIdIn(List<String> townQuotaIds) {
        kPIVillageQuotaRepository.deleteAllByTownQuotaIdIn(townQuotaIds);
    }

    @Override
    public void updateScoreById(String score, String scoreEnd, String id) {
        kPIVillageQuotaRepository.updateScoreById(score, scoreEnd, id);
    }

    @Override
    public List<KPIVillageQuota> findAllByTownQuotaIdAndQuarter(@Nonnull String townQuotaId, @Nonnull String quarter) {
        return kPIVillageQuotaRepository.findAllByTownQuotaIdAndQuarterOrderByDistrictId(townQuotaId, quarter);
    }

    @Override
    public List<KPIVillageQuota> findAllByParentQuotaId(String parentQuotaId) {
        return kPIVillageQuotaRepository.findAllByParentQuotaId(parentQuotaId);
    }

    private void handleAttachInfo(Map<String, List<Map<String, Object>>> cols, @NonNull String parentQuotaId, @NonNull String taskId, @NonNull String districtId) {
        List<Map<String, Object>> maps = cols.get(parentQuotaId);
        if (maps == null) {
            return;
        }
        KPIEvaluationSearchable kpiEvaluationSearchable = new KPIEvaluationSearchable();
        kpiEvaluationSearchable.setTaskId(taskId);
        kpiEvaluationSearchable.setDistrictId(districtId);

        DetectionIndexSearchable detectionIndexSearchable = new DetectionIndexSearchable();
        detectionIndexSearchable.setTaskId(taskId);
        detectionIndexSearchable.setDistrictId(districtId);
        List<DetectionIndex> all = detectionIndexService.findAll(detectionIndexSearchable);
        if (all.size() == 0) {
            return;
        }
        DetectionIndex detectionIndex = all.get(0);
        parentQuotaId = parentQuotaId.substring(4, parentQuotaId.length());
        switch (parentQuotaId) {
            case "0301":
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "年度村集体经济收入(全口径)");
                    put("value", detectionIndex.getEconomicIncome());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "本年度村集体经济收入");
                    put("value", detectionIndex.getCurrentYearEconomicIncome());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "上年度村集体经济收入");
                    put("value", detectionIndex.getCurrentYearEconomicIncome());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "年度村集体经济收入佐证");
                    put("value", detectionIndex.getIncomeSupportDoc());
                }});
                break;
            case "0302":
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "组织生活任务完成率");
                    put("value", detectionIndex.getPartyActivityFinishRatio());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "村干部员额数量");
                    put("value", detectionIndex.getCadrePosts());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "村干部35周岁以下");
                    put("value", detectionIndex.getCadreBelowThirtyFive());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "村干部35周岁到50周岁");
                    put("value", detectionIndex.getCadreBetweenThirtyFiveToFifty());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "村干部超过50周岁");
                    put("value", detectionIndex.getCadreOverFifty());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "挂牌清理完成率");
                    put("value", detectionIndex.getCleanFinishRatio());
                }});
                break;
            case "0303":
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "村级信访量");
                    put("value", detectionIndex.getPetitionLetterAmount());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "越级信访量");
                    put("value", detectionIndex.getSkipPetitionLetterAmount());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "信访化解量");
                    put("value", detectionIndex.getDefusePetitionLetterAmount());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "常住人口");
                    put("value", detectionIndex.getPopulation());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "信访佐证材料");
                    put("value", detectionIndex.getPetitionLetterSupportDoc());
                }});
                break;
            case "0304":
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "为民实事工程总额");
                    put("value", detectionIndex.getProjectAmount());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "总额佐证材料");
                    put("value", detectionIndex.getProjectAmountSupportDoc());
                }});
                break;
            case "0305":
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "人居环境是否通过抽查");
                    put("value", detectionIndex.getHasPass());
                }});
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "人居佐证");
                    put("value", detectionIndex.getEnvironmentSupportDoc());
                }});
                break;
            case "0501":
                kpiEvaluationSearchable.setType("1");
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "镇机关人员综合评议表");
                    put("value", kpiEvaluationService.findAll(kpiEvaluationSearchable, new Sort(Sort.Direction.ASC, "indexNum")));
                }});
                break;
            case "0502":
                kpiEvaluationSearchable.setType("3");
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "村“两委”干部综合评议表");
                    put("value", kpiEvaluationService.findAll(kpiEvaluationSearchable, new Sort(Sort.Direction.ASC, "indexNum")));
                }});
                break;
            case "0503":
                kpiEvaluationSearchable.setType("2");
                maps.add(new HashMap<String, Object>(){{
                    put("isQuota", "0");
                    put("quotasName", "专职村书记满意度调查表");
                    put("value", kpiEvaluationService.findAll(kpiEvaluationSearchable, new Sort(Sort.Direction.ASC, "indexNum")));
                }});
                break;
        }
    }

    private List<KPITownQuota> getKPITownQuota(String districtId, String makeQuotaDistrictId, String taskId, String parentQuotaId) {
        String townDistrictId = districtId.substring(0, 4);
        DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(KPITownQuota.class);
        detachedCriteria1.add(Restrictions.eq("districtId", townDistrictId));
        detachedCriteria1.add(Restrictions.eq("taskId", taskId));
        detachedCriteria1.add(Restrictions.like("parentQuotaId", parentQuotaId, MatchMode.START));
        detachedCriteria1.createAlias("kpiQuota", "k");
        detachedCriteria1.add(Restrictions.like("k.quotaScoringDepartId", makeQuotaDistrictId, MatchMode.ANYWHERE));
        return kpiTownQuotaService.findAll(detachedCriteria1);
    }

}
