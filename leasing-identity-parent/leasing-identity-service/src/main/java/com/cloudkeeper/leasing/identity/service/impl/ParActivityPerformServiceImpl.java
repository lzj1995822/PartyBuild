package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import com.cloudkeeper.leasing.identity.repository.ParActivityPerformRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityPerformService;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务执行记录 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityPerformServiceImpl extends BaseServiceImpl<ParActivityPerform> implements ParActivityPerformService {

    /** 任务执行记录 repository */
    private final ParActivityPerformRepository parActivityPerformRepository;

    @Override
    protected BaseRepository<ParActivityPerform> getBaseRepository() {
        return parActivityPerformRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("ActivityID", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains());
    }
//    @Override
//  public Page<ParActivityPerform> listAll(String activityId, String orgId,  Pageable pageable){
//        Page<ParActivityPerform> parActivityPerformPage = parActivityPerformRepository.listAll(activityId,orgId,pageable);
//        return parActivityPerformPage;
//    }
    @Override
    public List<PassPercentVO> perecent(String activityId){
        List<PassPercentVO> list = parActivityPerformRepository.perecent(activityId);
                return list;
    }
}
