package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.TVSignIn;
import com.cloudkeeper.leasing.identity.repository.TVSignInRepository;
import com.cloudkeeper.leasing.identity.service.TVSignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 远教视频签到记录 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TVSignInServiceImpl extends BaseServiceImpl<TVSignIn> implements TVSignInService {

    /** 远教视频签到记录 repository */
    private final TVSignInRepository tVSignInRepository;

    @Override
    protected BaseRepository<TVSignIn> getBaseRepository() {
        return tVSignInRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("signInRecord", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("signOutRecord", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public synchronized TVSignIn save(String id, Integer type, String username) {
        Optional<TVSignIn> byId = tVSignInRepository.findById(id);
        if(byId.isPresent()){
            TVSignIn tvSignIn = byId.get();
            if(type==0){
                tvSignIn.setSignInRecord(StringUtils.isEmpty(tvSignIn.getSignInRecord())? username: (tvSignIn.getSignInRecord()+" "+username));
            }
            if(type==1){
                tvSignIn.setSignOutRecord(StringUtils.isEmpty(tvSignIn.getSignOutRecord())? username: (tvSignIn.getSignOutRecord()+" "+username));
            }
            tVSignInRepository.save(tvSignIn);
        }
        return null;
    }

    @Override
    public TVSignIn updateFlag(String id, Integer flag) {
        Optional<TVSignIn> byId = tVSignInRepository.findById(id);
        if(byId.isPresent()){
            TVSignIn tvSignIn = byId.get();
            tvSignIn.setFlag(flag);
            tVSignInRepository.save(tvSignIn);
        }
        return null;
    }
}