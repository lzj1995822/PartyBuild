package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import com.cloudkeeper.leasing.identity.repository.CadreTaskObjectRepository;
import com.cloudkeeper.leasing.identity.service.CadreTaskObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 村书记模块发布任务对象记录 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadreTaskObjectServiceImpl extends BaseServiceImpl<CadreTaskObject> implements CadreTaskObjectService {

    /** 村书记模块发布任务对象记录 repository */
    private final CadreTaskObjectRepository cadreTaskObjectRepository;

    @Override
    protected BaseRepository<CadreTaskObject> getBaseRepository() {
        return cadreTaskObjectRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("objectType", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("note", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("townName", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public CadreTaskObject updateStatusByTaskIdAndObjectId(String status, String taskId, String objectId) {
        CadreTaskObject cadreTaskObject = cadreTaskObjectRepository.findByTaskIdAndAndObjectId(taskId, objectId);
        if (cadreTaskObject == null) {
            return null;
        }
        Integer statusNum = Integer.valueOf(status);
        cadreTaskObject.setStatus(String.valueOf(++statusNum));
        return save(cadreTaskObject);
    }

    @Override
    public void deleteByTaskId(String taskId) {
        cadreTaskObjectRepository.deleteAllByTaskId(taskId);
    }
}