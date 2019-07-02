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
    public TVSignIn save(String id, Integer type, String username) {
        Optional<TVSignIn> byId = tVSignInRepository.findById(id);
        if(byId.isPresent()){
            TVSignIn tvSignIn = byId.get();
            if(type==0){
                tvSignIn.setSignInRecord(tvSignIn.getSignInRecord()+" "+username);
            }
            if(type==1){
                tvSignIn.setSignOutRecord(tvSignIn.getSignOutRecord()+" "+username);
            }
        }
        return null;
    }
}