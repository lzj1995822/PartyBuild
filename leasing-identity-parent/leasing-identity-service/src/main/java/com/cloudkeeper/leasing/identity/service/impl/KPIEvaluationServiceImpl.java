package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.KPIEvaluation;
import com.cloudkeeper.leasing.identity.repository.KPIEvaluationRepository;
import com.cloudkeeper.leasing.identity.service.KPIEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 综合评议 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIEvaluationServiceImpl extends BaseServiceImpl<KPIEvaluation> implements KPIEvaluationService {

    /** 综合评议 repository */
    private final KPIEvaluationRepository kPIEvaluationRepository;

    @Override
    protected BaseRepository<KPIEvaluation> getBaseRepository() {
        return kPIEvaluationRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("indexNum", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("commentProject", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("commentContent", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void deleteByTypeAndTaskId(String type, String taskId) {
        kPIEvaluationRepository.deleteByTypeAndTaskId(type,taskId);
    }
}
