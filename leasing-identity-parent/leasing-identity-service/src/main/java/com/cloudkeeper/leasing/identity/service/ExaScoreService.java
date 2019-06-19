package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ExaScore;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.ExamScoreVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 考核积分 service
 * @author lxw
 */
public interface ExaScoreService extends BaseService<ExaScore> {

    List<ExamScoreVO> scoreCun(Pageable pageable, String sort,String year);
}
