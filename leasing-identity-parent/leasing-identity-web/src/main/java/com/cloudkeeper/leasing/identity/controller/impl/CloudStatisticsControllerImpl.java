package com.cloudkeeper.leasing.identity.controller.impl;


import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.CloudResult;
import com.cloudkeeper.leasing.identity.controller.CloudStatisticsController;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.CunScoreVO;
import com.cloudkeeper.leasing.identity.vo.CurrentActivityFormatVO;
import com.cloudkeeper.leasing.identity.vo.CurrentActivityVo;
import com.cloudkeeper.leasing.identity.vo.HistogramFormatVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 云平台
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CloudStatisticsControllerImpl implements CloudStatisticsController {


    private final SysDistrictService sysDistrictService;

    private final VillageCadresService villageCadresService;

    private final ParActivityPerformService parActivityPerformService;

    private final CadrePositionService cadrePositionService;

    private final ParMemberService parMemberService;

    private final InformationService informationService;

    private final ExaExamineService exaExamineService;


    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countOrganizationNumber() {
        Integer integer = sysDistrictService.countAllByDistrictId("01");
        return CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countPartyMemberNumber() {
        Integer integer = parMemberService.countAll("01");
        return CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Long> countVillageCadresNumber() {
        Long aLong = villageCadresService.countAllByDistrictId("01");
        return CloudResult.of(aLong);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countVillageSecretaryNumber() {
        Integer integer = cadrePositionService.countVillageSecretaryNumber("01", "SECRETARY");
        return CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countActivityPerformNumber() {
        Integer integer = parActivityPerformService.countAllByStatus("");
        return  CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countActivityFinishedNumber() {
        Integer integer = parActivityPerformService.countAllByStatus("2");
        return  CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<BigDecimal> countActivityCompleteRate() {
        Integer integer = parActivityPerformService.countAllByStatus("");
        Integer integer1 = parActivityPerformService.countAllByStatus("2");
        float rate = (float)integer1/integer;
        DecimalFormat df = new DecimalFormat("0.0000");//保留4位小数
        BigDecimal activityCompleteRate = new BigDecimal(df.format(rate));
        return CloudResult.of(activityCompleteRate);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Map<String, Object>> listCurrent() {
        CurrentActivityFormatVO formatOne = new CurrentActivityFormatVO("活动标题","left","title","",true,"50","2","");
        CurrentActivityFormatVO formatTwo = new CurrentActivityFormatVO("创建时间","right","creatTime","",true,"50","2","");
        List<CurrentActivityFormatVO> columns = new ArrayList<>();
        columns.add(formatOne);
        columns.add(formatTwo);
        List<CurrentActivityVo> rows = new ArrayList<>();
        List<CurrentActivityVo> current = informationService.findCurrent();
        current.forEach( item ->{
            CurrentActivityVo temp = new CurrentActivityVo();
            temp.setTitle(item.getTitle());
            temp.setCreatTime(item.getCreatTime());
            rows.add(temp);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("columns",columns);
        map.put("rows",rows);
        return CloudResult.of(map);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Map<String, Object>> cunRanking() {
        CurrentActivityFormatVO formatOne = new CurrentActivityFormatVO("村名","left","cun","",true,"50","2","");
        CurrentActivityFormatVO formatTwo = new CurrentActivityFormatVO("得分","right","exam","",true,"50","2","");
        List<CurrentActivityFormatVO> columns = new ArrayList<>();
        columns.add(formatOne);
        columns.add(formatTwo);
        List<CunScoreVO> rows = new ArrayList<>();
        List<CunScoreVO> cunScoreVOS = exaExamineService.cunScoreRank();
        cunScoreVOS.forEach( item->{
            CunScoreVO tempCun = new CunScoreVO();
            tempCun.setCun(item.getCun());
            tempCun.setExam(item.getExam());
            rows.add(tempCun);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("columns",columns);
        map.put("rows",rows);
        return CloudResult.of(map);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Map<String, Object>> townRanking() {
        List<String> categories = new ArrayList<>();
        List<HistogramFormatVo> series = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        List<CunScoreVO> townScoreVOS = exaExamineService.townScoreRank();
        townScoreVOS.forEach( item -> {
            categories.add(item.getCun());
            temp.add(item.getExam());
        });
        HistogramFormatVo formatVo = new HistogramFormatVo("分数",temp);
        series.add(formatVo);
        Map<String, Object> map = new HashMap<>();
        map.put("categories",categories);
        map.put("series",series);
        return CloudResult.of(map);
    }
}
