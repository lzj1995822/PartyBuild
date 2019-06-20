package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ExaExamine;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.ExamScoreVO;

import java.util.List;

/**
 * 考核审核 service
 * @author lxw
 */
public interface ExaExamineService extends BaseService<ExaExamine> {



    List<ExamScoreVO> scoreTown(String year);

    List<ExamScoreVO> scoreCun(String town);
}
