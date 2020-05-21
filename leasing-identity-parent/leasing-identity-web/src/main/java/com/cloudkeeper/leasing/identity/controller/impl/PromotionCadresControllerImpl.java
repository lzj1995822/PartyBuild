package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.PromotionCadresController;
import com.cloudkeeper.leasing.identity.domain.PromotionCadres;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.identity.dto.promotioncadres.PromotionCadresDTO;
import com.cloudkeeper.leasing.identity.dto.promotioncadres.PromotionCadresSearchable;
import com.cloudkeeper.leasing.identity.service.PromotionCadresService;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import com.cloudkeeper.leasing.identity.vo.PromotionCadresVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 村书记拟晋升名单 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PromotionCadresControllerImpl implements PromotionCadresController {

    /** 村书记拟晋升名单 service */
    private final PromotionCadresService promotionCadresService;

    private final VillageCadresService villageCadresService;

    @Override
    public Result<PromotionCadresVO> findOne(@ApiParam(value = "村书记拟晋升名单id", required = true) @PathVariable String id) {
        Optional<PromotionCadres> promotionCadresOptional = promotionCadresService.findOptionalById(id);
        return promotionCadresOptional.map(promotionCadres -> Result.of(promotionCadres.convert(PromotionCadresVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<PromotionCadresVO> add(@ApiParam(value = "村书记拟晋升名单 DTO", required = true) @RequestBody @Validated PromotionCadresDTO promotionCadresDTO) {
        PromotionCadres promotionCadres = promotionCadresService.save(promotionCadresDTO.convert(PromotionCadres.class));
        return Result.ofAddSuccess(promotionCadres.convert(PromotionCadresVO.class));
    }

    @Override
    public Result<PromotionCadresVO> update(@ApiParam(value = "村书记拟晋升名单id", required = true) @PathVariable String id,
        @ApiParam(value = "村书记拟晋升名单 DTO", required = true) @RequestBody @Validated PromotionCadresDTO promotionCadresDTO) {
        Optional<PromotionCadres> promotionCadresOptional = promotionCadresService.findOptionalById(id);
        if (!promotionCadresOptional.isPresent()) {
            return Result.ofLost();
        }
        PromotionCadres promotionCadres = promotionCadresOptional.get();
        BeanUtils.copyProperties(promotionCadresDTO, promotionCadres);
        promotionCadres = promotionCadresService.save(promotionCadres);
        return Result.ofUpdateSuccess(promotionCadres.convert(PromotionCadresVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村书记拟晋升名单id", required = true) @PathVariable String id) {
        promotionCadresService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<PromotionCadresVO>> list(@ApiParam(value = "村书记拟晋升名单查询条件", required = true) @RequestBody PromotionCadresSearchable promotionCadresSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<PromotionCadres> promotionCadresList = promotionCadresService.findAll(promotionCadresSearchable, sort);
        List<PromotionCadresVO> promotionCadresVOList = PromotionCadres.convert(promotionCadresList, PromotionCadresVO.class);
        return Result.of(promotionCadresVOList);
    }

    @Override
    public Result<Page<PromotionCadresVO>> page(@ApiParam(value = "村书记拟晋升名单查询条件", required = true) @RequestBody PromotionCadresSearchable promotionCadresSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<PromotionCadres> promotionCadresPage = promotionCadresService.findAll(promotionCadresSearchable, pageable);
        Page<PromotionCadresVO> promotionCadresVOPage = PromotionCadres.convert(promotionCadresPage, PromotionCadresVO.class);
        return Result.of(promotionCadresVOPage);
    }

    @PostMapping("/filterPromotion")
    @Transactional
    public Result<List<PromotionCadresVO>> filterPromotion(String status, @RequestBody List<String> ids) {
        ArrayList<PromotionCadresVO> promotionCadresVOS = new ArrayList<>();
        for (String item: ids) {
            PromotionCadres byId = promotionCadresService.findById(item);
            byId.setStatus(status);
            byId = promotionCadresService.save(byId);
            promotionCadresVOS.add(byId.convert(PromotionCadresVO.class));
        }
        return Result.of(promotionCadresVOS);
    }

    // 升级
    @GetMapping("/upgrade")
    @Transactional
    public Result<List<Map<String, String>>> upgrade(String taskId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PromotionCadres.class);
        detachedCriteria.add(Restrictions.eq("taskId", taskId));
        detachedCriteria.add(Restrictions.eq("status", "1"));
        List<PromotionCadres> all = promotionCadresService.findAll(detachedCriteria);
        List<Map<String, String>> map = new LinkedList<>();
        for (PromotionCadres item : all) {
            VillageCadres byId = villageCadresService.findById(item.getCadresId());
            if (byId == null) {
                continue;
            }
            map.add(new HashMap<String, String>(){{
                put("name", byId.getName());
                put("oldLevel", byId.getQuasiAssessmentRank());
                put("newLevel", item.getPurposeLevelName());
            }});
            byId.setQuasiAssessmentRank(item.getPurposeLevelName());
            villageCadresService.save(byId);
        }
        return Result.of(map);
    }

}