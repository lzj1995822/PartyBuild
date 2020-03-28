package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.RatingStandard;
import com.cloudkeeper.leasing.base.service.BaseService;
import io.swagger.models.auth.In;

/**
 * 村书记评级标准 service
 * @author asher
 */
public interface RatingStandardService extends BaseService<RatingStandard> {

    RatingStandard checkEnter(RatingStandard ratingStandard);

    // 一级检查
    Integer checkFirstLevel(RatingStandard standard, RatingStandard source);

    // 二级检查
    Integer checkSecondLevel(RatingStandard standard, RatingStandard source);

    // 三级检查
    Integer checkThirdLevel(RatingStandard standard, RatingStandard source);

    // 四级检查
    Integer checkFourthLevel(RatingStandard standard, RatingStandard source);

    // 五级检查
    Integer checkFifthLevel(RatingStandard standard, RatingStandard source);

    void deleteByCadresId(String cadresId);

    RatingStandard findByNameAndIsStandard(String name, String isStandard);
}