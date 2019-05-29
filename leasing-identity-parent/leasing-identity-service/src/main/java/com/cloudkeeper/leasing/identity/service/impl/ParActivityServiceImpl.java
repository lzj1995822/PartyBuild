package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.identity.domain.ParActivityReleaseFile;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivityDTO;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivitySearchable;
import com.cloudkeeper.leasing.identity.dto.paractivityreleasefile.ParActivityReleaseFileSearchable;
import com.cloudkeeper.leasing.identity.repository.ParActivityRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityService;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityServiceImpl extends BaseServiceImpl<ParActivity> implements ParActivityService {

    /** 活动 repository */
    private final ParActivityRepository parActivityRepository;

    private final ParActivityReleaseFileServiceImpl parActivityReleaseFileServiceImpl;
    @Override
    protected BaseRepository<ParActivity> getBaseRepository() {
        return parActivityRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("month", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("context", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtID", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("taskType", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("releaseTime", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("alarmTime", ExampleMatcher.GenericPropertyMatchers.contains());
    }
    //重写save方法
    @Nonnull
    @Override
    public ParActivityVO save(@Nonnull ParActivityDTO parActivityDTO) {
        ParActivity p = parActivityDTO.convert(ParActivity.class);
        ParActivity parActivity = super.save(p);
        handleReleaseFiles(parActivity.getId(), parActivityDTO.getFileUrls());
        return parActivity.convert(ParActivityVO.class);
    }

    private List<ParActivityReleaseFile> handleReleaseFiles(String activityId, List<String> fileUrls) {
        ParActivityReleaseFileSearchable parActivityReleaseFileSearchable = new ParActivityReleaseFileSearchable();
        parActivityReleaseFileSearchable.setActivityID(activityId);
        List<ParActivityReleaseFile> all = parActivityReleaseFileServiceImpl.findAll(parActivityReleaseFileSearchable);
        all.stream().forEach(item -> {
            parActivityReleaseFileServiceImpl.deleteById(item.getId());
        });

        ArrayList<ParActivityReleaseFile> results = new ArrayList<>();
        if(!StringUtils.isEmpty(fileUrls)) {
            fileUrls.stream().forEach(item -> {
                ParActivityReleaseFile parActivityReleaseFile = new ParActivityReleaseFile();
                parActivityReleaseFile.setActivityID(activityId);
                parActivityReleaseFile.setUrl(item);
                results.add(parActivityReleaseFileServiceImpl.save(parActivityReleaseFile));
            });
        }
        return results;
    }

    public void deleteAll(String id){
        parActivityRepository.deletePar(id);
        parActivityRepository.deleteParFile(id);
    }

    @Override
    public ParActivityVO updateAlarmTime(String id, LocalDateTime localDateTime) {
        ParActivity one = parActivityRepository.getOne(id);
        if (StringUtils.isEmpty(one)){
           return  null;
        }
        one.setAlarmTime(localDateTime);
        return  parActivityRepository.save(one).convert(ParActivityVO.class);
    }
}
