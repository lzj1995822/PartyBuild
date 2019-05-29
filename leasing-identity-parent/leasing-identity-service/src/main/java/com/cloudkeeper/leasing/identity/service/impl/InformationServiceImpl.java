package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import com.cloudkeeper.leasing.identity.domain.Information;
import com.cloudkeeper.leasing.identity.dto.information.InformationDTO;
import com.cloudkeeper.leasing.identity.repository.AcceptInformationRepository;
import com.cloudkeeper.leasing.identity.repository.InformationRepository;
import com.cloudkeeper.leasing.identity.service.AcceptInformationService;
import com.cloudkeeper.leasing.identity.service.InformationService;
import com.cloudkeeper.leasing.identity.vo.InformationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 消息通知 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InformationServiceImpl extends BaseServiceImpl<Information> implements InformationService {

    /** 消息通知 repository */
    private final InformationRepository informationRepository;

    private final AcceptInformationService acceptInformationService;

    @Override
    protected BaseRepository<Information> getBaseRepository() {
        return informationRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtID", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public InformationVO save(InformationDTO informationDTO) {
        Information information = informationDTO.convert(Information.class);
        super.save(information);

        InformationVO informationVO = information.convert(InformationVO.class);
        List<String> districtIdList = informationDTO.getDistrictIdList();
        districtIdList.stream().forEach(item -> {
            AcceptInformation acceptInformation = new AcceptInformation();
            acceptInformation.setAuthorId(information.getDistrictID());
            acceptInformation.setCreatTime(information.getCreatedAt());
            acceptInformation.setTitle(information.getTitle());
            acceptInformation.setInformationId(information.getId());
            acceptInformation.setObjs(item);
            acceptInformationService.save(acceptInformation);
        });
        return informationVO;
    }

    @Override
    public void deleteById(@Nonnull String id) {
        acceptInformationService.deleteAllByInformationId(id);
        super.deleteById(id);
    }
}