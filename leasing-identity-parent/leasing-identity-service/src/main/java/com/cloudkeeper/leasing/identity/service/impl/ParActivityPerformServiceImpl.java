package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.identity.domain.ParActivityExamine;
import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import com.cloudkeeper.leasing.identity.dto.paractivityperform.ParActivityPerformDTO;
import com.cloudkeeper.leasing.identity.repository.ParActivityExamineRepository;
import com.cloudkeeper.leasing.identity.repository.ParActivityObjectRepository;
import com.cloudkeeper.leasing.identity.repository.ParActivityPerformRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityPerformService;
import com.cloudkeeper.leasing.identity.service.ParActivityService;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import com.cloudkeeper.leasing.identity.vo.TownDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 任务执行记录 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityPerformServiceImpl extends BaseServiceImpl<ParActivityPerform> implements ParActivityPerformService {

    /** 任务执行记录 repository */
    private final ParActivityPerformRepository parActivityPerformRepository;

    private final ParActivityObjectRepository parActivityObjectRepository;

    private final ParActivityExamineRepository parActivityExamineRepository;

    private final ParActivityService parActivityService;
    @Override
    protected BaseRepository<ParActivityPerform> getBaseRepository() {
        return parActivityPerformRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("activityID", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains());
    }
//    @Override
//  public Page<ParActivityPerform> listAll(String activityId, String orgId,  Pageable pageable){
//        Page<ParActivityPerform> parActivityPerformPage = parActivityPerformRepository.listAll(activityId,orgId,pageable);
//        return parActivityPerformPage;
//    }
    @Override
    public List<PassPercentVO> percent(String activityId){
        if(StringUtils.isEmpty(activityId)){
            return null;
        }
        String sql =  "SELECT s6.*, ROUND(cast(s6.passed as FLOAT)/(s6.waitCheck + s6.passed + s6.fail),3)  as finishRatio from ( " +
                "SELECT " +
                " S5.tn, " +
                " COUNT( CASE WHEN sa = 1 THEN 1 ELSE NULL END ) waitCheck, " +
                " COUNT( CASE WHEN sa = 2 THEN 1 ELSE NULL END ) passed, " +
                " COUNT( CASE WHEN sa = 3 OR sa IS NULL THEN 1 ELSE NULL END ) fail  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " S3.town tn, " +
                " S3.cun cn, " +
                " S3.cunId cd, " +
                " S4.STATUS sa, " +
                " S4.ActivityID aid  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " S1.districtName town, " +
                " S0.districtName cun, " +
                " S0.id cunId  " +
                "FROM " +
                " SYS_District S0 " +
                " LEFT JOIN ( SELECT * FROM SYS_District sd WHERE sd.districtLevel = 2 ) S1 ON S0.attachTo = S1.districtId  " +
                "WHERE " +
                " S0.districtLevel = 3  " +
                " ) S3 " +
                " LEFT JOIN PAR_ActivityPerform S4 ON S3.cunId = S4.organizationId  " +
                " AND S4.ActivityID = '"+activityId+"'  " +
                " ) S5  " +
                "GROUP BY " +
                " tn " +
                " ) s6";
        List<PassPercentVO> list = super.findAllBySql(PassPercentVO.class, sql);
        return list;
    }

    @Override
    public List<TownDetailVO> townDetail(String activityId, String town){
        if(StringUtils.isEmpty(activityId) ||StringUtils.isEmpty(town)  ){
            return null;
        }
        String sql = "SELECT " +
                " S3.town tn, " +
                " S3.townId tnId, " +
                " S3.cun cn, " +
                " S3.cunId cd, " +
                " S4.STATUS sa, " +
                " S4.ActivityID aid  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " S1.districtName town, " +
                " S1.Id townId, " +
                " S0.districtName cun, " +
                " S0.id cunId  " +
                "FROM " +
                " SYS_District S0 " +
                " LEFT JOIN ( SELECT * FROM SYS_District sd WHERE sd.districtLevel = 2 ) S1 ON S0.attachTo = S1.districtId  " +
                "WHERE " +
                " S0.districtLevel = 3  " +
                " ) S3 " +
                " LEFT JOIN PAR_ActivityPerform S4 ON S3.cunId = S4.organizationId  " +
                " AND S4.ActivityID = '"+activityId+"'" +
                "WHERE " +
                " S3.town = '"+town+"'";
        List<TownDetailVO> list = super.findAllBySql(TownDetailVO.class, sql);
        return list;
    }

    @Transactional
    public ParActivityPerformVO check(ParActivityPerformDTO parActivityPerformDTO){
        if(StringUtils.isEmpty(parActivityPerformDTO)){
            return null;
        }
        Optional<ParActivityPerform> parActivityPerformFind =  parActivityPerformRepository.findByActivityIDAndOrganizationId(parActivityPerformDTO.getActivityID(),parActivityPerformDTO.getOrganizationId());
        Optional<ParActivityObject> parActivityObjectFind =  parActivityObjectRepository.findByActivityIdAndOrganizationId(parActivityPerformDTO.getActivityID(),parActivityPerformDTO.getDistrictId());
        if (parActivityPerformFind.isPresent()&&parActivityObjectFind.isPresent()) {
            //perform更新
            ParActivityPerform parActivityPerform = parActivityPerformFind.get();
            parActivityPerform.setStatus(parActivityPerformDTO.getStatus());
            parActivityPerform = super.save(parActivityPerform);
            //ParActivityObject更新
            ParActivityObject parActivityObject = parActivityObjectFind.get();
            parActivityObject.setStatus(parActivityPerformDTO.getStatus());
            parActivityObjectRepository.save(parActivityObject);
            //Exam添加
            ParActivityExamine parActivityExamine = new ParActivityExamine();
            parActivityExamine.setPId(parActivityPerform.getId());
            parActivityExamine.setRemark(parActivityPerformDTO.getRemark());
            parActivityExamineRepository.save(parActivityExamine);

            if(parActivityPerformDTO.getStatus().equals("2")){
                parActivityService.updateProgress(parActivityPerformDTO.getActivityID());
            }
            return parActivityPerform.convert(ParActivityPerformVO.class);
        }
       return null;
    }
}
