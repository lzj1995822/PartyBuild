package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParActivityObjectController;
import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectSearchable;
import com.cloudkeeper.leasing.identity.service.ParActivityObjectService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.ExamScoreDetailVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityObjectVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 任务对象 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityObjectControllerImpl implements ParActivityObjectController {

    /** 任务对象 service */
    private final ParActivityObjectService parActivityObjectService;

    private final SysLogService sysLogService;

    @Override
    public Result<ParActivityObjectVO> findOne(@ApiParam(value = "任务对象id", required = true) @PathVariable String id) {
        Optional<ParActivityObject> parActivityObjectOptional = parActivityObjectService.findOptionalById(id);
        return parActivityObjectOptional.map(parActivityObject -> Result.of(parActivityObject.convert(ParActivityObjectVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParActivityObjectVO> add(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO) {
        ParActivityObject parActivityObject = parActivityObjectService.save(parActivityObjectDTO.convert(ParActivityObject.class));
        return Result.ofAddSuccess(parActivityObject.convert(ParActivityObjectVO.class));
    }

    @Override
    public Result<ParActivityObjectVO> update(@ApiParam(value = "任务对象id", required = true) @PathVariable String id,
        @ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO) {
        Optional<ParActivityObject> parActivityObjectOptional = parActivityObjectService.findOptionalById(id);
        if (!parActivityObjectOptional.isPresent()) {
            return Result.ofLost();
        }
        ParActivityObject parActivityObject = parActivityObjectOptional.get();
        BeanUtils.copyProperties(parActivityObjectDTO, parActivityObject);
        parActivityObject = parActivityObjectService.save(parActivityObject);
        return Result.ofUpdateSuccess(parActivityObject.convert(ParActivityObjectVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "任务对象id", required = true) @PathVariable String id) {
        parActivityObjectService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Authorization(required = false)
    @Override
    public Result<List<ParActivityObjectVO>> list(@ApiParam(value = "任务对象查询条件", required = true) @RequestBody ParActivityObjectSearchable parActivityObjectSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        parActivityObjectService.updateIsWorking();
        List<ParActivityObject> parActivityObjectList = parActivityObjectService.findAll(parActivityObjectSearchable, sort);
        List<ParActivityObjectVO> parActivityObjectVOList = ParActivityObject.convert(parActivityObjectList, ParActivityObjectVO.class);
        return Result.of(parActivityObjectVOList);
    }

    @Override
    public Result<Page<ParActivityObjectVO>> page(@ApiParam(value = "任务对象查询条件", required = true) @RequestBody ParActivityObjectSearchable parActivityObjectSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        DetachedCriteria detachedCriteria = getDetachedCriteria(parActivityObjectSearchable);
        Integer totalCount = parActivityObjectService.getTotalCount(detachedCriteria);
        detachedCriteria.addOrder(Order.desc("p.month"));
        Page<ParActivityObject> all = parActivityObjectService.findAll(detachedCriteria, pageable, totalCount);
        Page<ParActivityObjectVO> parActivityObjectVOPage = ParActivityObject.convert(all, ParActivityObjectVO.class);
        return Result.of(parActivityObjectVOPage);
    }

    /**
     * 根据组织di和活动id查对应的对象记录
     * @param parActivityObjectDTO
     * @return
     */
    @Override
    @Authorization(required = false)
    public Result<ParActivityObjectVO> findByOrganizationIdAndActivityId(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO) {
        ParActivityObject parActivityObject = parActivityObjectService.findByOrganizationIdAndActivityId(parActivityObjectDTO.getOrganizationId(), parActivityObjectDTO.getActivityId());
        return Result.of(parActivityObject.convert(ParActivityObjectVO.class));
    }

    @Override
    public Result<Boolean> init() {
        parActivityObjectService.initPerActivity();
        return Result.of(true);
    }
    @Override
    @Transactional
    @Authorization(required = false)
    public  Result<List<ParActivityObjectVO>> execute(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO,
                                                      @ApiParam(value = "排序条件", required = true) Sort sort){
        List<ParActivityObjectVO> parActivityObjectVO = parActivityObjectService.execute(parActivityObjectDTO,sort);
        String  msg = parActivityObjectService.actionLog("正在执行",parActivityObjectVO.get(0).getTaskType(), parActivityObjectVO.get(0).getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,parActivityObjectService.getTableName(),parActivityObjectVO.get(0).getId());
        return Result.of(parActivityObjectVO);
    }

    @Override
    @Transactional
    @Authorization(required = false)
    public  Result<List<ParActivityObjectVO>> executeOver(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO,
                                                      @ApiParam(value = "排序条件", required = true) Sort sort){
        List<ParActivityObjectVO> parActivityObjectVO = parActivityObjectService.executeOver(parActivityObjectDTO,sort);
        String  msg = parActivityObjectService.actionLog("结束执行了",parActivityObjectVO.get(0).getTaskType(), parActivityObjectVO.get(0).getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,parActivityObjectService.getTableName(),parActivityObjectVO.get(0).getId());
        return Result.of(parActivityObjectVO);
    }

    @Override
    @Authorization(required = false)
    public  Result<List<ParActivityObjectVO>> TVIndexDetailList(@PathVariable String number){
        List<ParActivityObjectVO> parActivityObjectVO = parActivityObjectService.TVIndexDetailList(number);
        return Result.of(parActivityObjectVO);
    }

    @Override
    public Result<Integer> waitCheckNumber( @PathVariable String organizationId) {
        Integer integer = parActivityObjectService.waitCheckNumber(organizationId);
        return Result.of(integer);
    }


    private DetachedCriteria getDetachedCriteria(ParActivityObjectSearchable parActivityObjectSearchable) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ParActivityObject.class);
        detachedCriteria.createAlias("parActivity","p");
        if (!StringUtils.isEmpty(parActivityObjectSearchable.getAttachTo())) {
            detachedCriteria.add(Restrictions.eq("attachTo", parActivityObjectSearchable.getAttachTo()));
        }
        if (!StringUtils.isEmpty(parActivityObjectSearchable.getOrganizationId())) {
            detachedCriteria.add(Restrictions.eq("organizationId", parActivityObjectSearchable.getOrganizationId()));
        }
        if (!StringUtils.isEmpty(parActivityObjectSearchable.getStatus())) {
            detachedCriteria.add(Restrictions.eq("status", parActivityObjectSearchable.getStatus()));
        }
        if ("ACTIVE".equals(parActivityObjectSearchable.getCurrentStatus())) {
            detachedCriteria.add(Restrictions.le("p.month", lastDay()));
        } else if ("PLAN".equals(parActivityObjectSearchable.getCurrentStatus())) {
            detachedCriteria.add(Restrictions.gt("p.month", lastDay()));
        }
        return detachedCriteria;
    }

    private LocalDate lastDay() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = ca.getTime();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }
    @Override
    public Result<List<ExamScoreDetailVO>> examScoreDetail(String districtName,String year){
        List<ExamScoreDetailVO> list = parActivityObjectService.examScoreDetail(districtName,year);
        return Result.of(200,"查询成功",list);
    }

    @Override
    public Result<ParActivityObjectVO> officeExecute(@PathVariable String id) {
        ParActivityObject parActivityObject = parActivityObjectService.officeExecute(id);
        return Result.of(parActivityObject.convert(ParActivityObjectVO.class));
    }
}
