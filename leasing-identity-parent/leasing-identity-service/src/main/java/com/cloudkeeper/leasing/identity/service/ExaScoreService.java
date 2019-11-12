package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ExaScore;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.ActivityExamVO;
import com.cloudkeeper.leasing.identity.vo.ExamScoreAllVO;
import com.cloudkeeper.leasing.identity.vo.ExamScorePercentVO;
import com.cloudkeeper.leasing.identity.vo.ExamScoreVO;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 考核积分 service
 * @author lxw
 */
public interface ExaScoreService extends BaseService<ExaScore> {
    List<ExamScorePercentVO> percentTown(String year);
    List<ExamScorePercentVO> percentCun(String year,String townName);
    List<ExamScoreVO> scoreCun(Pageable pageable, String sort,String year, String districtType);
    List<ActivityExamVO> examScoreAll(Pageable pageable, String year, String search, String districtType);
}
