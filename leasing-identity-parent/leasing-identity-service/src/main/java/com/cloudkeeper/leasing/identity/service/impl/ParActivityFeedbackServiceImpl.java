package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivityFeedback;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.dto.paractivityfeedback.ParActivityFeedbackSearchable;
import com.cloudkeeper.leasing.identity.repository.ParActivityFeedbackRepository;
import com.cloudkeeper.leasing.identity.repository.SysUserRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityFeedbackService;
import com.cloudkeeper.leasing.identity.vo.ParActivityFeedbackVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 移动端执行记录 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityFeedbackServiceImpl extends BaseServiceImpl<ParActivityFeedback> implements ParActivityFeedbackService {

    /** 移动端执行记录 repository */
    private final ParActivityFeedbackRepository parActivityFeedbackRepository;

    private final SysUserRepository sysUserRepository;

    @Override
    protected BaseRepository<ParActivityFeedback> getBaseRepository() {
        return parActivityFeedbackRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("snId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("context", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("time", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("flag", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public Page<ParActivityFeedbackVO> phonePage(ParActivityFeedbackSearchable parActivityFeedbackSearchable, Pageable pageable){
        parActivityFeedbackSearchable.setOrgId(parActivityFeedbackSearchable.getUserId());
        List<SysUser> findAllByOrganizationId = sysUserRepository.findAllByOrganizationId(parActivityFeedbackSearchable.getOrgId());

        String uid = findAllByOrganizationId.get(0).getId();
        parActivityFeedbackSearchable.setUserId(uid);
        parActivityFeedbackSearchable.setOrgId(null);
        Page<ParActivityFeedback> page = this.findAll(parActivityFeedbackSearchable,pageable);
        Page<ParActivityFeedbackVO> phonePage = ParActivityFeedback.convert(page,ParActivityFeedbackVO.class);
        return phonePage;
    }

}
