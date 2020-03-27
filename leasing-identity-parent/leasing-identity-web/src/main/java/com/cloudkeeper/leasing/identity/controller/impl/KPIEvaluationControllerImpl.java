package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPIEvaluationController;
import com.cloudkeeper.leasing.identity.domain.KPIEvaluation;
import com.cloudkeeper.leasing.identity.dto.kpievaluation.KPIEvaluationDTO;
import com.cloudkeeper.leasing.identity.dto.kpievaluation.KPIEvaluationSearchable;
import com.cloudkeeper.leasing.identity.service.KPIEvaluationService;
import com.cloudkeeper.leasing.identity.vo.KPIEvaluationVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 综合评议 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIEvaluationControllerImpl implements KPIEvaluationController {

    /** 综合评议 service */
    private final KPIEvaluationService kPIEvaluationService;
    private final StringRedisTemplate template;
    private static final Logger logger = LoggerFactory.getLogger(KPIEvaluationController.class);
    @Override
    public Result<KPIEvaluationVO> findOne(@ApiParam(value = "综合评议id", required = true) @PathVariable String id) {
        Optional<KPIEvaluation> kPIEvaluationOptional = kPIEvaluationService.findOptionalById(id);
        return kPIEvaluationOptional.map(kPIEvaluation -> Result.of(kPIEvaluation.convert(KPIEvaluationVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<KPIEvaluationVO> add(@ApiParam(value = "综合评议 DTO", required = true) @RequestBody @Validated KPIEvaluationDTO kPIEvaluationDTO) {
        KPIEvaluation kPIEvaluation = kPIEvaluationService.save(kPIEvaluationDTO.convert(KPIEvaluation.class));
        return Result.ofAddSuccess(kPIEvaluation.convert(KPIEvaluationVO.class));
    }

    @Override
    public Result<KPIEvaluationVO> update(@ApiParam(value = "综合评议id", required = true) @PathVariable String id,
        @ApiParam(value = "综合评议 DTO", required = true) @RequestBody @Validated KPIEvaluationDTO kPIEvaluationDTO) {
        Optional<KPIEvaluation> kPIEvaluationOptional = kPIEvaluationService.findOptionalById(id);
        if (!kPIEvaluationOptional.isPresent()) {
            return Result.ofLost();
        }
        KPIEvaluation kPIEvaluation = kPIEvaluationOptional.get();
        BeanUtils.copyProperties(kPIEvaluationDTO, kPIEvaluation);
        kPIEvaluation = kPIEvaluationService.save(kPIEvaluation);
        return Result.ofUpdateSuccess(kPIEvaluation.convert(KPIEvaluationVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "综合评议id", required = true) @PathVariable String id) {
        kPIEvaluationService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<KPIEvaluationVO>> list(@ApiParam(value = "综合评议查询条件", required = true) @RequestBody KPIEvaluationSearchable kPIEvaluationSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<KPIEvaluation> kPIEvaluationList = kPIEvaluationService.findAll(kPIEvaluationSearchable, sort);
        List<KPIEvaluationVO> kPIEvaluationVOList = KPIEvaluation.convert(kPIEvaluationList, KPIEvaluationVO.class);
        return Result.of(kPIEvaluationVOList);
    }

    @Override
    public Result<Page<KPIEvaluationVO>> page(@ApiParam(value = "综合评议查询条件", required = true) @RequestBody KPIEvaluationSearchable kPIEvaluationSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KPIEvaluation> kPIEvaluationPage = kPIEvaluationService.findAll(kPIEvaluationSearchable, pageable);
        Page<KPIEvaluationVO> kPIEvaluationVOPage = KPIEvaluation.convert(kPIEvaluationPage, KPIEvaluationVO.class);
        return Result.of(kPIEvaluationVOPage);
    }

    @Override
    @Transactional
    public Result<Object> addEvaluations(@RequestBody List<KPIEvaluationDTO> kPIEvaluationDTO) {
        int index = 1;
        for (KPIEvaluationDTO k : kPIEvaluationDTO){
            kPIEvaluationService.deleteByTypeAndTaskId(k.getType(),k.getTaskId());
        }
        for (KPIEvaluationDTO k : kPIEvaluationDTO){
            k.setIndexNum(String.valueOf(index));
            index++;
            k.setId(null);
            KPIEvaluation kpiEvaluation = k.convert(KPIEvaluation.class);
            kPIEvaluationService.save(kpiEvaluation);
        }
        return Result.of(true);
    }

    @Override
    public Result<Object> getEvaluations(@RequestBody KPIEvaluationSearchable kpiEvaluationSearchable) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIEvaluation.class);
        detachedCriteria.add(Restrictions.eq("taskId",kpiEvaluationSearchable.getTaskId()));
        detachedCriteria.add(Restrictions.eq("type",kpiEvaluationSearchable.getType()));
        detachedCriteria.addOrder(Order.asc("indexNum"));
        detachedCriteria.add(Restrictions.or(Restrictions.ne("commentContent",""), Restrictions.isNotNull("commentContent")));
        List<KPIEvaluation> list = kPIEvaluationService.findAll(detachedCriteria);
        Map<String,Object> map = new HashMap<>();
        Field impl = null;
        try {
            if (CollectionUtils.isNotEmpty(list)){
                //不为空获取本taskId数据
                //清空上一次detachedCriteria
                impl = detachedCriteria.getClass().getDeclaredField("impl");
                impl.setAccessible(true);
                CriteriaImpl cimpl = (CriteriaImpl) impl.get(detachedCriteria);
                Field criterionEntries = cimpl.getClass().getDeclaredField("criterionEntries");
                criterionEntries.setAccessible(true);
                criterionEntries.set(cimpl, new ArrayList());


                detachedCriteria.add(Restrictions.eq("taskId",kpiEvaluationSearchable.getTaskId()));
                detachedCriteria.add(Restrictions.eq("type",kpiEvaluationSearchable.getType()));
                detachedCriteria.add(Restrictions.or(Restrictions.eq("commentContent",""), Restrictions.isNull("commentContent")));
                List<KPIEvaluation> list1 = kPIEvaluationService.findAll(detachedCriteria);
                map.put("base",list);
                map.put("endOne",list1);
            }else {
                //获取基础模板数据
                impl = detachedCriteria.getClass().getDeclaredField("impl");
                impl.setAccessible(true);
                CriteriaImpl cimpl = (CriteriaImpl) impl.get(detachedCriteria);
                Field criterionEntries = cimpl.getClass().getDeclaredField("criterionEntries");
                criterionEntries.setAccessible(true);
                criterionEntries.set(cimpl, new ArrayList());

                detachedCriteria.add(Restrictions.eq("taskId","-1"));
                detachedCriteria.add(Restrictions.eq("type",kpiEvaluationSearchable.getType()));
                detachedCriteria.add(Restrictions.or(Restrictions.ne("commentContent",""), Restrictions.isNotNull("commentContent")));

                list = kPIEvaluationService.findAll(detachedCriteria);
                criterionEntries.setAccessible(true);
                criterionEntries.set(cimpl, new ArrayList());

                detachedCriteria.add(Restrictions.eq("taskId","-1"));
                detachedCriteria.add(Restrictions.eq("type",kpiEvaluationSearchable.getType()));
                detachedCriteria.add(Restrictions.or(Restrictions.eq("commentContent",""), Restrictions.isNull("commentContent")));
                List<KPIEvaluation> list1 = kPIEvaluationService.findAll(detachedCriteria);
                map.put("base",list);
                map.put("endOne",list1);
            }

        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return  Result.of(map);
    }

    @Override
    public Result<Object> testRedisChannel() {
        template.convertAndSend("checkHasCompleted","测试消息---"+Math.random());
        return Result.of(true);
    }

}
