package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ExaExamine;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.CunScoreVO;
import com.cloudkeeper.leasing.identity.vo.ExamScoreVO;

import java.util.List;

/**
 * 考核审核 service
 * @author lxw
 */
public interface ExaExamineService extends BaseService<ExaExamine> {

    List<ExamScoreVO> scoreTown(String year);

    List<ExamScoreVO> scoreCun(String year,String town);

    //云图村排名
    List<CunScoreVO> cunScoreRank();
   //云图镇排名
    List<CunScoreVO> townScoreRank();
}
