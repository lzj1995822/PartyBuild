package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.RatingStandard;
import com.cloudkeeper.leasing.identity.repository.RatingStandardRepository;
import com.cloudkeeper.leasing.identity.service.RatingStandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 村书记评级标准 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RatingStandardServiceImpl extends BaseServiceImpl<RatingStandard> implements RatingStandardService {

    // 能力研判-合格
    private static final String NLYP_HEGE = "合格";

    // 能力研判-良好
    private static final String NLYP_LIANGHAO = "良好";

    // 能力研判-优秀
    private static final String NLYP_YOUXIU = "优秀";

    // 年度考核等次-优秀
    private static final String NDKH_YOUXIU = "优秀";

    // 年度考核等次-称职
    private static final String NDKH_CHENZHI = "称职";

    // 年度考核等次-基本称职
    private static final String NDKH_JIBENCHENZHI = "基本称职";

    // 年度考核等次-不合格
    private static final String NDKH_BUHEGE = "不合格";

    /** 村书记评级标准 repository */
    private final RatingStandardRepository ratingStandardRepository;

    @Override
    protected BaseRepository<RatingStandard> getBaseRepository() {
        return ratingStandardRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("workDuration", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("abilityJudgement", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("lastGrade", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("gradeTimes", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("gradeLastTimes", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("aGradeLastTimes", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("aGradeTimes", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("topTenGradeLastTimes", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("topFiveGradeLastTimes", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("topThreeGradeLastTimes", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("honoursType", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public RatingStandard checkEnter(RatingStandard ratingStandard) {
        List<RatingStandard> allByIsStandard = ratingStandardRepository.findAllByIsStandard("1");
        List<Integer> res = new ArrayList<>();
        for (RatingStandard item:allByIsStandard) {
            switch (item.getName()) {
                case "一级":
                    res.add(checkFirstLevel(item, ratingStandard));
                    break;
                case "二级":
                    res.add(checkSecondLevel(item, ratingStandard));
                    break;
                case "三级":
                    res.add(checkThirdLevel(item, ratingStandard));
                    break;
                case "四级":
                    res.add(checkFourthLevel(item, ratingStandard));
                    break;
                case "五级":
                    res.add(checkFifthLevel(item, ratingStandard));
                    break;
                default:
                    res.add(0);
            }
        }
        Integer max = Collections.max(res);
        if (max == 0) {
            return null;
        }
        return allByIsStandard.get(--max);
    }

    @Override
    public Integer checkFirstLevel(RatingStandard standard, RatingStandard source) {
        BigDecimal workDuration = new BigDecimal(source.getWorkDuration());
        boolean b = workDuration.compareTo(new BigDecimal(standard.getWorkDuration())) == 1;
        BigDecimal abilityJudgement = new BigDecimal(source.getAbilityJudgement());
        boolean b1 = abilityJudgement.compareTo(new BigDecimal(standard.getAbilityJudgement())) >= 0;
        BigDecimal lastGrade = new BigDecimal(source.getLastGrade());
        boolean b2 = lastGrade.compareTo(new BigDecimal(standard.getLastGrade())) >= 0;
        return b && b1 && b2 ? 1 : 0;
    }

    @Override
    public Integer checkSecondLevel(RatingStandard standard, RatingStandard source) {
        BigDecimal workDuration = new BigDecimal(source.getWorkDuration());
        boolean b = workDuration.compareTo(new BigDecimal(standard.getWorkDuration())) == 1;
        BigDecimal abilityJudgement = new BigDecimal(source.getAbilityJudgement());
        boolean b1 = abilityJudgement.compareTo(new BigDecimal(standard.getAbilityJudgement())) >= 0;
        BigDecimal gradeTimes = new BigDecimal(source.getGradeTimes());
        boolean b2 = gradeTimes.compareTo(new BigDecimal(standard.getGradeTimes())) >= 0;

        BigDecimal gradeLastTimes = new BigDecimal(source.getGradeLastTimes());
        boolean a = gradeLastTimes.compareTo(new BigDecimal(standard.getGradeLastTimes())) >= 0;
        return (b && b1 && b2 || a) ? 2 : 0;
    }

    @Override
    public Integer checkThirdLevel(RatingStandard standard, RatingStandard source) {
        BigDecimal workDuration = new BigDecimal(source.getWorkDuration());
        boolean b = workDuration.compareTo(new BigDecimal(standard.getWorkDuration())) == 1;
        BigDecimal abilityJudgement = new BigDecimal(source.getAbilityJudgement());
        boolean b1 = abilityJudgement.compareTo(new BigDecimal(standard.getAbilityJudgement())) >= 0;
        BigDecimal gradeTimes = new BigDecimal(source.getAGradeLastTimes());
        boolean b2 = gradeTimes.compareTo(new BigDecimal(standard.getAGradeLastTimes())) >= 0;

        BigDecimal aGradeTimes = new BigDecimal(source.getAGradeTimes());
        boolean a = aGradeTimes.compareTo(new BigDecimal(standard.getAGradeTimes())) >= 0;

        boolean c = standard.getHonoursType().contains(source.getHonoursType());
        return (b && b1 && b2 || a || c) ? 3 : 0;
    }

    @Override
    public Integer checkFourthLevel(RatingStandard standard, RatingStandard source) {
        BigDecimal workDuration = new BigDecimal(source.getWorkDuration());
        boolean b = workDuration.compareTo(new BigDecimal(standard.getWorkDuration())) == 1;
        BigDecimal abilityJudgement = new BigDecimal(source.getAbilityJudgement());
        boolean b1 = abilityJudgement.compareTo(new BigDecimal(standard.getAbilityJudgement())) >= 0;
        BigDecimal gradeTimes = new BigDecimal(source.getTopTenGradeLastTimes());
        boolean b2 = gradeTimes.compareTo(new BigDecimal(standard.getTopTenGradeLastTimes())) >= 0;

        BigDecimal aGradeTimes = new BigDecimal(source.getTopFiveGradeLastTimes());
        boolean a = aGradeTimes.compareTo(new BigDecimal(standard.getTopFiveGradeLastTimes())) >= 0;

        boolean c = standard.getHonoursType().contains(source.getHonoursType());
        return (b && b1 && b2 || a || c) ? 4 : 0;
    }

    @Override
    public Integer checkFifthLevel(RatingStandard standard, RatingStandard source) {
        BigDecimal workDuration = new BigDecimal(source.getWorkDuration());
        boolean b = workDuration.compareTo(new BigDecimal(standard.getWorkDuration())) == 1;
        BigDecimal abilityJudgement = new BigDecimal(source.getAbilityJudgement());
        boolean b1 = abilityJudgement.compareTo(new BigDecimal(standard.getAbilityJudgement())) >= 0;
        BigDecimal gradeTimes = new BigDecimal(source.getTopThreeGradeLastTimes());
        // 职级五 对同一指标有两种标准值前端逗号拼接标准值
        String[] split = standard.getTopThreeGradeLastTimes().split(",");
        boolean b2 = gradeTimes.compareTo(new BigDecimal(split[0])) >= 0;

        BigDecimal aGradeTimes = new BigDecimal(source.getTopThreeGradeLastTimes());
        boolean a = aGradeTimes.compareTo(new BigDecimal(split[1])) >= 0;

        boolean c = standard.getHonoursType().contains(source.getHonoursType());
        return (b && b1 && b2 || a || c) ? 5 : 0;
    }
}