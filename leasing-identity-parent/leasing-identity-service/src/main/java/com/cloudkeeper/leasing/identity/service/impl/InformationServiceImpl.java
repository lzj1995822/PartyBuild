package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import com.cloudkeeper.leasing.identity.domain.Information;
import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import com.cloudkeeper.leasing.identity.dto.information.InformationDTO;
import com.cloudkeeper.leasing.identity.repository.AcceptInformationRepository;
import com.cloudkeeper.leasing.identity.repository.InformationRepository;
import com.cloudkeeper.leasing.identity.repository.MessageCenterRepository;
import com.cloudkeeper.leasing.identity.service.AcceptInformationService;
import com.cloudkeeper.leasing.identity.service.InformationService;
import com.cloudkeeper.leasing.identity.service.MessageCenterService;
import com.cloudkeeper.leasing.identity.vo.CurrentActivityVo;
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

    private final MessageCenterService messageCenterService;


    @Override
    protected BaseRepository<Information> getBaseRepository() {
        return informationRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtID", ExampleMatcher.GenericPropertyMatchers.startsWith());
    }

    @Override
    public InformationVO save(InformationDTO informationDTO) {
        Information information = informationDTO.convert(Information.class);
        information = super.save(information);

        InformationVO informationVO = information.convert(InformationVO.class);
        List<String> districtIdList = informationDTO.getDistrictIdList();
        for (String item : districtIdList) {
            AcceptInformation acceptInformation = new AcceptInformation();
            acceptInformation.setAuthorId(information.getDistrictID());
            acceptInformation.setCreatTime(information.getCreatedAt());
            acceptInformation.setTitle(information.getTitle());
            acceptInformation.setInformationId(information.getId());
            acceptInformation.setStatus("0");
            acceptInformation.setObjs(item);
            acceptInformationService.save(acceptInformation);

            messageCenterService.save(information.getId(),item,"information");
        }
        return informationVO;
    }

    @Override
    public List<CurrentActivityVo> findCurrent() {
        String sql="select '[通知公告]       '+ title as title,CONVERT(VARCHAR(100),createdAt,23) as creatTime from INF_information WHERE year(createdAt)=YEAR(GETDATE()) AND month(createdAt)=month(GETDATE()) " +
                "UNION ALL " +
                "SELECT '[党建任务]       '+title as title ,CONVERT(VARCHAR(100),month,23) as creatTime from PAR_Activity WHERE taskType='party' and year(month)=YEAR(GETDATE()) AND month(month)=month(GETDATE()) " +
                "UNION ALL " +
                "SELECT '[远教任务]       '+title as title,CONVERT(VARCHAR(100),month,23) as creatTime from PAR_Activity WHERE taskType='distLearning'and year(month)=YEAR(GETDATE()) AND month(month)=month(GETDATE()) ";
        List<CurrentActivityVo> allBySql = findAllBySql(CurrentActivityVo.class, sql);
        return allBySql;
    }

    @Override
    public void deleteById(@Nonnull String id) {
        acceptInformationService.deleteAllByInformationId(id);
        super.deleteById(id);
    }
}